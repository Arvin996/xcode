package cn.xk.xcode.aop;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.RefreshDict;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.utils.spring.SpringExpressionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/26 14:57
 * @Version 1.0.0
 * @Description DictRefreshAspect
 **/
@Slf4j
@Aspect
@RequiredArgsConstructor
public class DictRefreshAspect {

    private final DictCacheStrategy dictCacheStrategy;

    @Around("@annotation(refreshDict)")
    public Object around(ProceedingJoinPoint joinPoint, RefreshDict refreshDict) {
        String dictType = refreshDict.dictType();
        String dictCode = refreshDict.dictCode();
        String dictName = refreshDict.dictName();
        String dictDesc = refreshDict.dictDesc();
        List<String> expressionList = new ArrayList<>();
        expressionList.add(dictType);
        expressionList.add(dictCode);
        expressionList.add(dictName);
        if (StrUtil.isNotBlank(dictDesc)) {
            expressionList.add(dictDesc);
        }
        Map<String, Object> parsedExpressions = SpringExpressionUtil.parseExpressions(joinPoint, expressionList);
        if (!dictCacheStrategy.containsKey(parsedExpressions.getOrDefault(dictType, "").toString())) {
            log.warn("字典类型{}不存在", dictType);
        } else {
            if (Objects.isNull(parsedExpressions.get(dictCode)) || Objects.isNull(parsedExpressions.get(dictName))) {
                log.warn("字典code{}或者字典name{}解析失败", dictCode, dictName);
            } else {
                DictDataEntity dictDataEntity = new DictDataEntity();
                dictDataEntity.setCode(parsedExpressions.get(dictCode).toString());
                dictDataEntity.setName(parsedExpressions.get(dictName).toString());
                dictDataEntity.setDictType(parsedExpressions.get(dictType).toString());
                dictDataEntity.setDesc(parsedExpressions.get(dictDesc).toString());
                dictCacheStrategy.saveOrUpdateDictData(dictDataEntity);
            }
        }
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return proceed;
    }
}
