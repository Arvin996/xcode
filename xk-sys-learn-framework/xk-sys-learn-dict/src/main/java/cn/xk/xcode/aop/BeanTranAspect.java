package cn.xk.xcode.aop;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.annotation.BeanDictTrans;
import cn.xk.xcode.annotation.BeanPropertyDictTrans;
import cn.xk.xcode.annotation.ListDictTrans;
import cn.xk.xcode.client.RpcDictClient;
import cn.xk.xcode.context.DictContext;
import cn.xk.xcode.entity.DataTableDict;
import cn.xk.xcode.entity.DictCodeMethod;
import cn.xk.xcode.entity.TransPojo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/5/31 15:09
 * @Version 1.0
 */
@Component
@Aspect
public class BeanTranAspect {

    @Resource
    private DictContext dictContext;

    @Resource
    private FeignClientBuilder feignClientBuilder;

    @Around(value = "@annotation(cn.xk.xcode.annotation.DictTrans) || @within(cn.xk.xcode.annotation.DictTrans)")
    public Object process(ProceedingJoinPoint point) throws IllegalAccessException {
        Object proceed;
        try {
            proceed = point.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if (proceed instanceof TransPojo) {
            handlerSingleTrans((TransPojo) proceed);
        } else if (proceed instanceof List) {
            List<?> list = Convert.toList(proceed);
            if (list != null && !list.isEmpty()) {
                Object o = list.get(0);
                if (o instanceof TransPojo) {
                    proceed = list.stream().map(pojo -> {
                        try {
                            return handlerSingleTrans((TransPojo) pojo);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }).collect(Collectors.toList());
                }
            }
        } else {
            // 处理 apiResult类型 或者map 统一返回值类型
            Map<String, Object> objectMap = BeanUtil.beanToMap(proceed);
            Object body = objectMap.getOrDefault("body", null);
            if (Objects.nonNull(body)) {
                if (body instanceof TransPojo) {
                    objectMap.put("body", handlerSingleTrans((TransPojo) body));
                } else if (body instanceof List) {
                    List<?> list = Convert.toList(body);
                    if (list != null && !list.isEmpty()) {
                        Object o = list.get(0);
                        if (o instanceof TransPojo) {
                            objectMap.put("body", list.stream().map(pojo -> {
                                try {
                                    return handlerSingleTrans((TransPojo) pojo);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }).collect(Collectors.toList()));
                        }
                    }
                }
            }
        }
        return proceed;
    }

    private TransPojo handlerSingleTrans(TransPojo transPojo) throws IllegalAccessException {
        Field[] declaredFields = transPojo.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(BeanPropertyDictTrans.class)) {
                BeanPropertyDictTrans beanDictTrans = field.getAnnotation(BeanPropertyDictTrans.class);
                String parentId = beanDictTrans.parentId();
                String targetField = beanDictTrans.targetField();
                Field tatgetClassField = ReflectUtil.getField(transPojo.getClass(), targetField);
                String fieldValue = ReflectUtil.getFieldValue(transPojo, field).toString();
                if (!Objects.isNull(tatgetClassField)) {
                    DictCodeMethod dictCodeMethod = beanDictTrans.dictCodeMethod();
                    tatgetClassField.setAccessible(true);
                    if (dictCodeMethod.equals(DictCodeMethod.LOCAL)) {
                        tatgetClassField.set(transPojo, dictContext.getValue(parentId, fieldValue));
                    } else {
                        String servicedName = beanDictTrans.serviceName();
                        RpcDictClient rpcDictClient = feignClientBuilder.forType(RpcDictClient.class, servicedName).build();
                        // 远程调用
                        tatgetClassField.set(transPojo
                                , rpcDictClient
                                        .rpcTrans(DataTableDict
                                                .builder().parId(parentId).code(fieldValue).build()));
                    }

                }
            } else if (field.isAnnotationPresent(ListDictTrans.class)) {
                if (field.getType().isAssignableFrom(List.class)) {
                    List<?> list = Convert.toList(ReflectUtil.getFieldValue(transPojo, field));
                    if (!Objects.isNull(list) && !list.isEmpty()) {
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
            } else if (field.isAnnotationPresent(BeanDictTrans.class)) {
                Object fieldValue = ReflectUtil.getFieldValue(transPojo, field);
                if (fieldValue instanceof TransPojo) {
                    handlerSingleTrans((TransPojo) fieldValue);
                }
            }
        }
        return transPojo;
    }

}
