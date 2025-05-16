package cn.xk.xcode.core.handler;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.client.TransFlexClient;
import cn.xk.xcode.core.entity.FlexTransDto;
import cn.xk.xcode.core.entity.TransPo;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.core.service.FlexTransService;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.xk.xcode.constants.TransFlexGlobalConstants.TRANS_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 15:32
 * @description
 */
@Slf4j
@SuppressWarnings("all")
public class TransFlexHandler {
    private TransFlexHandler() {
    }

    @SuppressWarnings("unchecked")
    public static void resolveFlexLocalTrans(FlexFieldTrans flexTrans, Field field, TransVo proceed, FlexTransService flexTransService) {
        if (!validate(flexTrans, proceed)) {
            return;
        }
        Field tagetField = ReflectUtil.getField(proceed.getClass(), flexTrans.targetField());
        tagetField.setAccessible(true);
        String refSourceFiled = flexTrans.refSourceFiled();
        if (List.class.isAssignableFrom(field.getType())) {
            List<? extends TransPo> list = flexTransService.findList((Collection<Serializable>) ReflectUtil.getFieldValue(proceed, field), flexTrans.ref(), flexTrans.refConditionField());
            if (Objects.isNull(list) || list.isEmpty()) {
                log.warn("list翻译对象为空");
                return;
            }
            if (List.class.isAssignableFrom(tagetField.getType())) {
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, BeanUtil.toBean(list, tagetField.getType()));
                    } else {
                        tagetField.set(proceed, list.stream().map(transVo -> ReflectUtil.getFieldValue(transVo, refSourceFiled)).collect(Collectors.toList()));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            } else {
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, BeanUtil.toBean(list.get(0), tagetField.getType()));
                    } else {
                        tagetField.set(proceed, ReflectUtil.getFieldValue(list.get(0), refSourceFiled));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            }
        } else {
            if (List.class.isAssignableFrom(tagetField.getType())) {
                List<? extends TransPo> list = flexTransService.findList((Serializable) ReflectUtil.getFieldValue(proceed, field), flexTrans.ref(), flexTrans.refConditionField());
                if (Objects.isNull(list) || list.isEmpty()) {
                    log.warn("list翻译对象为空");
                    return;
                }
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, BeanUtil.toBean(list, tagetField.getType()));
                    } else {
                        tagetField.set(proceed, list.stream().map(transVo -> ReflectUtil.getFieldValue(transVo, refSourceFiled)).collect(Collectors.toList()));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }

            } else {
                TransPo transPo = flexTransService.findById((Serializable) ReflectUtil.getFieldValue(proceed, field), flexTrans.ref(), flexTrans.refConditionField());
                if (Objects.isNull(transPo)) {
                    log.warn("transVo翻译对象为空");
                    return;
                }
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, BeanUtil.toBean(transPo, tagetField.getType()));
                    } else {
                        tagetField.set(proceed, ReflectUtil.getFieldValue(transPo, refSourceFiled));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            }
        }
    }

    public static void resolveFlexRpcTrans(FlexFieldTrans flexTrans, Field field, TransVo proceed, FeignClientBuilder feignClientBuilder) {
        if (validate(flexTrans, proceed)) {
            return;
        }
        String serviceName = flexTrans.serviceName();
        if (!StringUtils.hasLength(serviceName)) {
            log.error("翻译出错，服务名未定义");
            return;
        }
        Field tagetField = ReflectUtil.getField(proceed.getClass(), flexTrans.targetField());
        tagetField.setAccessible(true);
        String refSourceFiled = flexTrans.refSourceFiled();
        TransFlexClient transFlexClient = feignClientBuilder.forType(TransFlexClient.class, serviceName).build();
        if (List.class.isAssignableFrom(field.getType())) {
            List<? extends TransPo> list = transFlexClient.flexTransList(FlexTransDto.builder().
                    targetClazz(flexTrans.ref()).
                    id((Serializable) ReflectUtil.getFieldValue(proceed, field)).
                    conditionField(flexTrans.refConditionField()).build());
            if (Objects.isNull(list) || list.isEmpty()) {
                log.warn("远程list翻译对象为空");
                return;
            }
            if (List.class.isAssignableFrom(tagetField.getType())) {
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, list);
                    } else {
                        tagetField.set(proceed, list.stream().map(transVo -> ReflectUtil.getFieldValue(transVo, refSourceFiled)).collect(Collectors.toList()));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            } else {
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, list.get(0));
                    } else {
                        tagetField.set(proceed, ReflectUtil.getFieldValue(list.get(0), refSourceFiled));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            }
        } else {
            if (List.class.isAssignableFrom(tagetField.getType())) {
                List<? extends TransPo> list = transFlexClient.flexTransList(FlexTransDto.builder().
                        targetClazz(flexTrans.ref()).
                        id((Serializable) ReflectUtil.getFieldValue(proceed, field)).
                        conditionField(flexTrans.refConditionField()).build());
                if (Objects.isNull(list) || list.isEmpty()) {
                    log.warn("list翻译对象为空");
                    return;
                }
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, list);
                    } else {
                        tagetField.set(proceed, list.stream().map(transVo -> ReflectUtil.getFieldValue(transVo, refSourceFiled)).collect(Collectors.toList()));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            } else {
                TransPo transPo = transFlexClient.flexTrans(FlexTransDto.builder().
                        targetClazz(flexTrans.ref()).
                        id((Serializable) ReflectUtil.getFieldValue(proceed, field)).
                        conditionField(flexTrans.refConditionField()).build());
                if (Objects.isNull(transPo)) {
                    log.warn("transVo翻译对象为空");
                    return;
                }
                try {
                    if (StrUtil.isBlank(refSourceFiled)) {
                        tagetField.set(proceed, BeanUtil.toBean(transPo, tagetField.getType()));
                    } else {
                        tagetField.set(proceed, ReflectUtil.getFieldValue(transPo, refSourceFiled));
                    }
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                    throw new ServerException(TRANS_FAILED);
                }
            }
        }
    }

    private static boolean validate(FlexFieldTrans flexTrans, TransVo proceed) {
        String targetField = flexTrans.targetField();
        String refConditionField = flexTrans.refConditionField();
        if (!StringUtils.hasLength(targetField)) {
            log.error("targetField 不能为空");
            return false;
        }
        if (!StringUtils.hasLength(refConditionField)) {
            log.error("refConditionField 不能为空");
            return false;
        }
        if (Objects.isNull(ReflectUtil.getField(proceed.getClass(), flexTrans.targetField()))) {
            log.error("翻译对象目标属性{}不存在", targetField);
            return false;
        }
        return true;
    }
}
