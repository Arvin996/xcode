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
import org.springframework.core.Ordered;

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
public class DictRefreshAspect implements Ordered {

    private final DictCacheStrategy dictCacheStrategy;

    @Around("@annotation(refreshDict)")
    public Object around(ProceedingJoinPoint joinPoint, RefreshDict refreshDict) throws Throwable {
        String dictType = refreshDict.dictType();
        String dictCode = refreshDict.dictCode();
        String dictName = refreshDict.dictName();
        String dictDesc = refreshDict.dictDesc();
        List<String> expressionList = new ArrayList<>();
        expressionList.add(dictType);
        expressionList.add(dictCode);
        expressionList.add(dictName);
        boolean flag = true;
        if (StrUtil.isNotBlank(dictDesc)) {
            expressionList.add(dictDesc);
        }
        DictDataEntity dictDataEntity = new DictDataEntity();
        Map<String, Object> parsedExpressions = SpringExpressionUtil.parseExpressions(joinPoint, expressionList);
        if (!dictCacheStrategy.containsKey(parsedExpressions.getOrDefault(dictType, "").toString())) {
            log.warn("字典类型{}不存在", dictType);
            flag = false;
        } else {
            if (Objects.isNull(parsedExpressions.get(dictCode)) || Objects.isNull(parsedExpressions.get(dictName))) {
                log.warn("字典code{}或者字典name{}解析失败", dictCode, dictName);
                flag = false;
            } else {
                dictDataEntity.setCode(parsedExpressions.get(dictCode).toString());
                dictDataEntity.setName(parsedExpressions.get(dictName).toString());
                dictDataEntity.setDictType(parsedExpressions.get(dictType).toString());
                dictDataEntity.setDesc(parsedExpressions.get(dictDesc).toString());

            }
        }
        Object proceed;
        try {
            proceed = joinPoint.proceed();
            if (flag){
                dictCacheStrategy.saveOrUpdateDictData(dictDataEntity);
            }
        } catch (Throwable e) {
            if (refreshDict.isExceptionRefresh()){
                dictCacheStrategy.saveOrUpdateDictData(dictDataEntity);
            }
            throw e;
        }
        return proceed;
    }

    @Override
    public int getOrder() {
        return 6;
    }
}
