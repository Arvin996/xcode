package cn.xk.xcode.core.handler;

import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.core.annotation.FlexEnumTrans;
import cn.xk.xcode.core.client.TransFlexClient;
import cn.xk.xcode.core.entity.EnumTransDto;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

import static cn.xk.xcode.constants.TransFlexGlobalConstants.TRANS_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 15:32
 * @description
 */
@Slf4j
public class TransEnumHandler {
    private TransEnumHandler() {
    }

    public static void resolveEnumLocalTrans(FlexEnumTrans enumTrans, Field field, Object proceed, GlobalEnumsContext globalEnumsContext) {
        if (validate(enumTrans, proceed)) {
            return;
        }
        String enumType = enumTrans.enumType();
        Field targetField = ReflectUtil.getField(proceed.getClass(), enumTrans.targetField());
        // 源翻译值
        String key = ReflectUtil.getFieldValue(proceed, field).toString();
        Object value = globalEnumsContext.getValue(enumType, key);
        if (Objects.isNull(value)) {
            log.error("枚举类型{}没有对应的key:{}，翻译失败", enumType, key);
            return;
        }
        targetField.setAccessible(true);
        try {
            targetField.set(proceed, value);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new ServerException(TRANS_FAILED);
        }
    }

    public static void resolveEnumRpcTrans(FlexEnumTrans enumTrans, Field field, Object proceed, FeignClientBuilder feignClientBuilder) {
        if (validate(enumTrans, proceed)) {
            return;
        }
        String serviceName = enumTrans.serviceName();
        if (!StringUtils.hasLength(serviceName)) {
            log.error("枚举翻译出错，服务名未定义");
            return;
        }
        Field targetField = ReflectUtil.getField(proceed.getClass(), enumTrans.targetField());
        String key = ReflectUtil.getFieldValue(proceed, field).toString();
        targetField.setAccessible(true);
        try {
            TransFlexClient transFlexClient = feignClientBuilder.forType(TransFlexClient.class, serviceName).build();
            Object value = transFlexClient.enumTrans(EnumTransDto.builder().enumType(enumTrans.enumType()).key(key).build());
            if (Objects.isNull(value)) {
                log.error("服务{}的枚举类型{}没有对应的key:{}，翻译失败", serviceName, enumTrans.enumType(), key);
                return;
            }
            targetField.set(proceed, value);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new ServerException(TRANS_FAILED);
        }
    }

    private static boolean validate(FlexEnumTrans enumTrans, Object proceed) {
        String enumType = enumTrans.enumType();
        if (!StringUtils.hasLength(enumType)) {
            log.error("枚举类型未定义，翻译失败");
            return true;
        }
        String targetField = enumTrans.targetField();
        if (!StringUtils.hasLength(targetField)) {
            log.error("枚举翻译的目标字段为空，翻译失败");
            return true;
        }
        Field field = ReflectUtil.getField(proceed.getClass(), targetField);
        if (Objects.isNull(field)) {
            log.error("枚举翻译的目标字段属性{}不存在，翻译失败", targetField);
            return true;
        }
        return false;
    }
}
