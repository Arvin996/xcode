package cn.xk.xcode.handler;

import cn.hutool.core.util.ClassUtil;
import cn.xk.xcode.annotation.DictType;
import cn.xk.xcode.config.DictEnumPackages;
import cn.xk.xcode.entity.DataTableDict;
import cn.xk.xcode.entity.IEnumDict;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/7/4 16:34
 * @Version 1.0
 * @Description EnumDictHandler
 */
@Slf4j
public class EnumDictHandler extends AbstractDataDictHandler {

    private final DictEnumPackages dictEnumPackages;

    public EnumDictHandler(DictEnumPackages dictEnumPackages) {
        super();
        this.dictEnumPackages = dictEnumPackages;
    }

    // 这里只能做成配置类 做成list
    @Override
    void loadDictCache() {
        // 扫描当前工程中的枚举类
        List<String> packages = dictEnumPackages.getPackages();
        Set<Class<?>> classes = new HashSet<>();
        for (String pack : packages) {
            classes.addAll(ClassUtil.scanPackage(pack));
        }
        // 将枚举类的数据加载到缓存中
        for (Class<?> aClass : classes) {
            if (ClassUtil.isEnum(aClass)) {
                if (aClass.isAnnotationPresent(DictType.class)) {
                    DictType dictType = aClass.getAnnotation(DictType.class);
                    if (aClass.isAssignableFrom(IEnumDict.class)) {
                        String type = dictType.dictType();
                        String name = dictType.dictName();
                        if (!StringUtils.hasLength(type)){
                            log.warn("枚举类{}没有设置字典类型", aClass.getName());
                            continue;
                        }
                        if (!StringUtils.hasLength(name)){
                            log.warn("枚举类{}没有设置字典名称", aClass.getName());
                            continue;
                        }
                        if (cache.containsKey(type)){
                            log.warn("枚举类{}字典类型定义重复", aClass.getName());
                            continue;
                        }
                        cache.computeIfAbsent("##", k -> new ArrayList<>());
                        cache.get("##").add(DataTableDict.builder().code(type).name(name).parId("##").build());
                        List<DataTableDict> enumDict = new ArrayList<>();
                        for (Object enumConstant : aClass.getEnumConstants()) {
                            IEnumDict dict = (IEnumDict) enumConstant;
                            DataTableDict dataTableDict = new DataTableDict();
                            dataTableDict.setCode(dict.getCode());
                            dataTableDict.setName(dict.getName());
                            dataTableDict.setParId(type);
                            enumDict.add(dataTableDict);
                        }
                        cache.computeIfAbsent(type,  k -> new ArrayList<>());
                        cache.get(type).addAll(enumDict);
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
