package cn.xk.xcode.handler;

import cn.hutool.core.util.ClassUtil;
import cn.xk.xcode.annotation.DictType;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.config.XcodeDictProperties;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.entity.IEnumDict;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/7/4 16:34
 * @Version 1.0
 * @Description EnumDictHandler
 */
@Slf4j
public class EnumDictHandler extends AbstractDataDictHandler {

    private final XcodeDictProperties xcodeDictProperties;

    public EnumDictHandler(XcodeDictProperties xcodeDictProperties, DictCacheStrategy dictCacheStrategy) {
        super(dictCacheStrategy);
        this.xcodeDictProperties = xcodeDictProperties;
    }

    @Override
    public void loadDictCache() {
        // 扫描当前工程中的枚举类
        List<String> dictEnumPackages = xcodeDictProperties.getEnumDictPackages();
        if (CollectionUtil.isEmpty(dictEnumPackages)) {
            log.info("未配置枚举类扫描包路径");
            return;
        }
        Set<Class<?>> classes = new HashSet<>();
        for (String pack : dictEnumPackages) {
            classes.addAll(ClassUtil.scanPackage(pack));
        }
        // 将枚举类的数据加载到缓存中
        for (Class<?> aClass : classes) {
            if (ClassUtil.isEnum(aClass)) {
                if (aClass.isAnnotationPresent(DictType.class)) {
                    DictType dictType = aClass.getAnnotation(DictType.class);
                    if (IEnumDict.class.isAssignableFrom(aClass)) {
                        String type = dictType.dictType();
                        String name = dictType.dictName();
                        if (!StringUtils.hasLength(type)) {
                            log.warn("枚举类{}没有设置字典类型", aClass.getName());
                            continue;
                        }
                        if (!StringUtils.hasLength(name)) {
                            log.warn("枚举类{}没有设置字典名称", aClass.getName());
                            continue;
                        }
                        if (dictCacheStrategy.containsKey(type)) {
                            log.warn("枚举类{}字典类型定义重复", aClass.getName());
                            continue;
                        }
                        for (Object enumConstant : aClass.getEnumConstants()) {
                            IEnumDict dict = (IEnumDict) enumConstant;
                            DictDataEntity dictDataEntity = new DictDataEntity();
                            dictDataEntity.setCode(dict.getCode());
                            dictDataEntity.setName(dict.getName());
                            dictDataEntity.setDictType(type);
                            dictCacheStrategy.saveOrUpdateDictData(dictDataEntity);
                        }
                    } else {
                        log.warn("枚举类{}没有实现IEnumDict接口", aClass.getName());
                    }
                } else {
                    log.warn("枚举类{}没有添加@DictType注解", aClass.getName());
                }
            }
        }
    }
}
