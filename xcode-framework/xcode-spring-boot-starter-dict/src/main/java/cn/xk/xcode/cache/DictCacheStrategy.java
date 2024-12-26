package cn.xk.xcode.cache;

import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/12/26 10:58
 * @Version 1.0.0
 * @Description DictCacheStrategy
 **/
public interface DictCacheStrategy {

    void saveOrUpdateDictData(DictDataEntity dictDataEntity);

    void removeDictData(String dictCode, String dictType);

    DictDataEntity getDictData(String dictCode, String dictType);

    List<DictDataEntity> getDictTypeList(String dictType);

    void clearCache();

    boolean containsKey(String dictType);

    void removeDictType(String dictType);

    default String getDictName(String dictCode, String dictType){
        DictDataEntity dictData = getDictData(dictCode, dictType);
        if (Objects.isNull(dictData)){
            return null;
        }
        return dictData.getName();
    }

    default String getDictDesc(String dictCode, String dictType){
        DictDataEntity dictData = getDictData(dictCode, dictType);
        if (Objects.isNull(dictData)){
            return null;
        }
        return dictData.getDesc();
    }

    default void removeDictData(DictDataEntity dictDataEntity){
        removeDictData(dictDataEntity.getCode(), dictDataEntity.getDictType());
    }

    default void batchSaveOrUpdateDictData(List<DictDataEntity> dictDataEntities){
        dictDataEntities.forEach(this::saveOrUpdateDictData);
    }

    default List<String> getPropertiesByDictType(String dictType, Function<DictDataEntity, String> func){
        List<DictDataEntity> dictTypeList = getDictTypeList(dictType);
        if (CollectionUtil.isEmpty(dictTypeList)){
            return CollectionUtil.createEmptyList();
        }
        return dictTypeList.stream().map(func).collect(Collectors.toList());
    }
}
