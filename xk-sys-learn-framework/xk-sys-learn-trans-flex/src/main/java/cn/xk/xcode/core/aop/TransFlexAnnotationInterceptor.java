package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.EnumTrans;
import cn.xk.xcode.core.annotation.FlexTrans;
import cn.xk.xcode.core.handler.TransEnumHandler;
import cn.xk.xcode.core.handler.TransFlexHandler;
import cn.xk.xcode.core.service.FlexTransService;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.List;

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
        Object data;
        if (proceed instanceof CommonResult) {
            data = ((CommonResult<?>) proceed).getData();
            if (data instanceof List) {
                ((List<?>) data).forEach(this::handlerSingleObject);
            } else {
                handlerSingleObject(data);
            }
        }
        return proceed;
    }

    private void handlerSingleObject(Object proceed) {
        Field[] fields = proceed.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EnumTrans.class)) {
                handlerEnumTrans(field, proceed);
            } else if (field.isAnnotationPresent(FlexTrans.class)) {
                handlerFlexTrans(field, proceed);
            }
        }
    }

    private void handlerFlexTrans(Field field, Object proceed) {
        FlexTrans flexTrans = field.getAnnotation(FlexTrans.class);
        if (flexTrans.isRpc()) {
            TransFlexHandler.resolveFlexRpcTrans(flexTrans, field, proceed, feignClientBuilder);
        } else {
            TransFlexHandler.resolveFlexLocalTrans(flexTrans, field, proceed, flexTransService);
        }
    }

    private void handlerEnumTrans(Field field, Object proceed) {
        EnumTrans enumTrans = field.getAnnotation(EnumTrans.class);
        if (enumTrans.isRpc()) {
            TransEnumHandler.resolveEnumRpcTrans(enumTrans, field, proceed, feignClientBuilder);
        } else {
            TransEnumHandler.resolveEnumLocalTrans(enumTrans, field, proceed, globalEnumsContext);
        }
    }
}
