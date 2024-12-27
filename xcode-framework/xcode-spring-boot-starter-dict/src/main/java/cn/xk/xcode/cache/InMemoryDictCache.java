package cn.xk.xcode.cache;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeDictProperties;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.collections.MapUtil;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/12/26 10:59
 * @Version 1.0.0
 * @Description InMemoryDictCache
 **/
@NoArgsConstructor
public class InMemoryDictCache implements DictCacheStrategy{

    private static final ConcurrentHashMap<String, Map<String, DictDataEntity>> LOCAL_DICT_CACHE = new ConcurrentHashMap<>();

    @Override
    public void saveOrUpdateDictData(DictDataEntity dictDataEntity) {
        String code = dictDataEntity.getCode();
        String dictType = dictDataEntity.getDictType();
        Map<String, DictDataEntity> map = LOCAL_DICT_CACHE.getOrDefault(dictType, null);
        if (MapUtil.isEmpty(map)){
            map = new HashMap<>();
            map.put(code, dictDataEntity);
            LOCAL_DICT_CACHE.put(dictType, map);
        }else {
            DictDataEntity dataEntity = map.getOrDefault(code, dictDataEntity);
            if (Objects.isNull(dataEntity)){
                map.put(code, dictDataEntity);
            }else {
                String name = dictDataEntity.getName();
                String desc = dictDataEntity.getDesc();
                dataEntity.setName(name);
                if (StrUtil.isNotBlank(desc)){
                    dataEntity.setDesc(desc);
                }
                map.put(code, dataEntity);
            }

        }
    }

    @Override
    public void removeDictData(String dictCode, String dictType) {
        Map<String, DictDataEntity> map = LOCAL_DICT_CACHE.getOrDefault(dictType, null);
        if (!MapUtil.isEmpty(map)){
            map.remove(dictCode);
        }
    }

    @Override
    public DictDataEntity getDictData(String dictCode, String dictType) {
        Map<String, DictDataEntity> map = LOCAL_DICT_CACHE.getOrDefault(dictType, null);
        if (!MapUtil.isEmpty(map)){
            return map.getOrDefault(dictCode, null);
        }
        return null;
    }

    @Override
    public List<DictDataEntity> getDictTypeList(String dictType) {
        Map<String, DictDataEntity> map = LOCAL_DICT_CACHE.getOrDefault(dictType, null);
        if (!MapUtil.isEmpty(map)){
            return new ArrayList<>(map.values());
        }
        return CollectionUtil.createEmptyList();
    }

    @Override
    public void clearCache() {
        LOCAL_DICT_CACHE.clear();
    }

    @Override
    public boolean containsKey(String dictType) {
        return LOCAL_DICT_CACHE.containsKey(dictType);
    }

    @Override
    public void removeDictType(String dictType) {
        LOCAL_DICT_CACHE.remove(dictType);
    }

    @Override
    public XcodeDictProperties.CacheType type() {
        return XcodeDictProperties.CacheType.LOCAL;
    }
}
