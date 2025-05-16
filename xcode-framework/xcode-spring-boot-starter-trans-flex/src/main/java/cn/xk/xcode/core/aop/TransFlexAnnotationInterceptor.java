package cn.xk.xcode.core.aop;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.core.annotation.FlexEnumTrans;
import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.annotation.IgnoreRecFlexTrans;
import cn.xk.xcode.core.annotation.IgnoreTrans;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.core.handler.TransEnumHandler;
import cn.xk.xcode.core.handler.TransFlexHandler;
import cn.xk.xcode.core.service.FlexTransService;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.constants.TransFlexGlobalConstants.TRANS_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 13:58
 * @description
 */
@Slf4j
@RequiredArgsConstructor
public class TransFlexAnnotationInterceptor implements MethodInterceptor {

    private final GlobalEnumsContext globalEnumsContext;
    private final FeignClientBuilder feignClientBuilder;
    private final FlexTransService flexTransService;

    @Override
    public Object invoke(@NonNull MethodInvocation invocation) {
        Object proceed;
        try {
            proceed = invocation.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
            throw new ServerException(TRANS_FAILED);
        }
        try {
            if (invocation.getMethod().isAnnotationPresent(IgnoreTrans.class)){
                return proceed;
            }
            Object data;
            if (proceed instanceof CommonResult) {
                CommonResult<?> commonResult = (CommonResult<?>) proceed;
                CommonResult<Object> objectCommonResult = new CommonResult<>();
                objectCommonResult.setCode(commonResult.getCode());
                objectCommonResult.setMsg(commonResult.getMsg());
                data = commonResult.getData();
                if (data instanceof List) {
                    List<?> list = Convert.toList(data);
                    if (list != null && !list.isEmpty()) {
                        Object o = list.get(0);
                        if (o instanceof TransVo) {
                            data = list.stream().map(pojo -> handleSingleObject((TransVo) pojo)).collect(Collectors.toList());
                        }
                    }
                } else if (data instanceof TransVo) {
                    handleSingleObject((TransVo) data);
                } else if (data instanceof PageResult) {
                    PageResult<?> pageResult = (PageResult<?>) data;
                    List<?> resultData = pageResult.getData();
                    if (resultData != null && !resultData.isEmpty()) {
                        Object o = resultData.get(0);
                        if (o instanceof TransVo) {
                            resultData = resultData.stream().map(pojo -> handleSingleObject((TransVo) pojo)).collect(Collectors.toList());
                            data = new PageResult<>(pageResult.getPageParam(), pageResult.getTotal(), resultData);
                        }
                    }
                } else {
                    log.warn("返回值为未知类型，暂时不支持翻译， 返回值：{}", data);
                }
                objectCommonResult.setData(data);
                return objectCommonResult;
            }
        } catch (Exception e) {
            log.error("翻译异常,错误信息：{}", e.getMessage());
            return proceed;
        }
        return proceed;
    }

    private TransVo handleSingleObject(TransVo proceed) {
        Field[] fields = proceed.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FlexEnumTrans.class)) {
                handleEnumTrans(field, proceed);
            } else if (field.isAnnotationPresent(FlexFieldTrans.class)) {
                handleFlexTrans(field, proceed);
            } else {
                if (List.class.isAssignableFrom(field.getType())) {
                    if (!field.isAnnotationPresent(IgnoreRecFlexTrans.class)) {
                        List<?> list = Convert.toList(ReflectUtil.getFieldValue(proceed, field));
                        if (list != null && !list.isEmpty()) {
                            Object o = list.get(0);
                            if (o instanceof TransVo) {
                                list.forEach(
                                        transVo -> {
                                            TransVo tempVo = (TransVo) transVo;
                                            handleSingleObject(tempVo);
                                        }
                                );
                            }
                        }
                    }
                } else if (TransVo.class.isAssignableFrom(field.getType())) {
                    if (!field.isAnnotationPresent(IgnoreRecFlexTrans.class)) {
                        handleSingleObject((TransVo) ReflectUtil.getFieldValue(proceed, field));
                    }
                }
            }
        }
        return proceed;
    }

    private void handleFlexTrans(Field field, TransVo proceed) {
        FlexFieldTrans flexTrans = field.getAnnotation(FlexFieldTrans.class);
        if (flexTrans.isRpc()) {
            TransFlexHandler.resolveFlexRpcTrans(flexTrans, field, proceed, feignClientBuilder);
        } else {
            TransFlexHandler.resolveFlexLocalTrans(flexTrans, field, proceed, flexTransService);
        }
    }

    private void handleEnumTrans(Field field, TransVo proceed) {
        FlexEnumTrans enumTrans = field.getAnnotation(FlexEnumTrans.class);
        if (enumTrans.isRpc()) {
            TransEnumHandler.resolveEnumRpcTrans(enumTrans, field, proceed, feignClientBuilder);
        } else {
            TransEnumHandler.resolveEnumLocalTrans(enumTrans, field, proceed, globalEnumsContext);
        }
    }
}
