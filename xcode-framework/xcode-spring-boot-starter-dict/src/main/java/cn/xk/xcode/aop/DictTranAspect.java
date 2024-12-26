package cn.xk.xcode.aop;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.annotation.DictFieldTrans;
import cn.xk.xcode.annotation.IgnoreRecursionTrans;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.client.RpcDictClient;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.entity.DictTranType;
import cn.xk.xcode.entity.TransPojo;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.openfeign.FeignClientBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/5/31 15:09
 * @Version 1.0
 */
@RequiredArgsConstructor
@Aspect
@Slf4j
public class DictTranAspect {

    private final FeignClientBuilder feignClientBuilder;
    private final DictCacheStrategy dictCacheStrategy;

    private final ErrorCode RESULT_TYPE_ERROR = new IntErrorCode(1200_0_500, "返回类型不是commonResult");

    @Around(value = "@annotation(cn.xk.xcode.annotation.DictTrans) || @within(cn.xk.xcode.annotation.DictTrans)")
    public Object process(ProceedingJoinPoint point) {
        Object proceed;
        try {
            proceed = point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if (!(proceed instanceof CommonResult)) {
            throw new ServerException(RESULT_TYPE_ERROR);
        }
        try {
            CommonResult<?> commonResult = (CommonResult<?>) proceed;
            CommonResult<Object> objectCommonResult = new CommonResult<>();
            Object data = commonResult.getData();
            if (data instanceof TransPojo) {
                handlerSingleTrans((TransPojo) data);
            } else if (data instanceof List) {
                List<?> list = Convert.toList(data);
                if (list != null && !list.isEmpty()) {
                    Object o = list.get(0);
                    if (o instanceof TransPojo) {
                        data = list.stream().map(pojo -> {
                            try {
                                return handlerSingleTrans((TransPojo) pojo);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList());
                    }
                }
            }
            objectCommonResult.setCode(commonResult.getCode());
            objectCommonResult.setMsg(commonResult.getMsg());
            objectCommonResult.setData(data);
            return objectCommonResult;
        } catch (Exception e) {
            log.error("字典翻译异常,错误信息：{}", e.getMessage());
            return proceed;
        }
    }

    private TransPojo handlerSingleTrans(TransPojo transPojo) throws IllegalAccessException {
        Field[] declaredFields = transPojo.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(DictFieldTrans.class)) {
                if (!ClassUtil.isBasicType(field.getType()) && !field.getType().equals(String.class)) {
                    log.warn("字典翻译注解DictFieldTrans作用于字段:{}.{}错误，只能用在基本类型或者String类型上", transPojo.getClass().getName(), field.getName());
                    continue;
                }
                DictFieldTrans dictFieldTrans = field.getAnnotation(DictFieldTrans.class);
                String dictType = dictFieldTrans.dictType();
                String targetField = dictFieldTrans.targetField();
                Field tatgetClassField = ReflectUtil.getField(transPojo.getClass(), targetField);
                String fieldValue = ReflectUtil.getFieldValue(transPojo, field).toString();
                if (Objects.nonNull(tatgetClassField)) {
                    DictTranType dictTranType = dictFieldTrans.dictTranType();
                    tatgetClassField.setAccessible(true);
                    if (dictTranType.equals(DictTranType.LOCAL)) {
                        tatgetClassField.set(transPojo, dictCacheStrategy.getDictName(fieldValue, dictType));
                    } else {
                        String servicedName = dictFieldTrans.serviceName();
                        RpcDictClient rpcDictClient = feignClientBuilder.forType(RpcDictClient.class, servicedName).build();
                        // 远程调用
                        tatgetClassField.set(transPojo, rpcDictClient.rpcTrans(DictDataEntity.builder().dictType(dictType).code(fieldValue).build()));
                    }
                }
            } else {
                if (List.class.isAssignableFrom(field.getType())) {
                    if (!field.isAnnotationPresent(IgnoreRecursionTrans.class)) {
                        List<?> list = Convert.toList(ReflectUtil.getFieldValue(transPojo, field));
                        if (CollectionUtil.isNotEmpty(list)) {
                            Object o = list.get(0);
                            if (o instanceof TransPojo) {
                                list.forEach(
                                        pojo -> {
                                            TransPojo tempPojo = (TransPojo) pojo;
                                            try {
                                                handlerSingleTrans(tempPojo);
                                            } catch (IllegalAccessException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                );
                            }
                        }
                    }
                } else {
                    Object fieldValue = ReflectUtil.getFieldValue(transPojo, field);
                    if (fieldValue instanceof TransPojo) {
                        if (!field.isAnnotationPresent(IgnoreRecursionTrans.class)) {
                            handlerSingleTrans((TransPojo) fieldValue);
                        }
                    }
                }
            }
        }
        return transPojo;
    }

}
