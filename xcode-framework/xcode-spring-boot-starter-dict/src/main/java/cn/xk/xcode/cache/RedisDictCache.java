package cn.xk.xcode.cache;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.collections.MapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/12/26 10:59
 * @Version 1.0.0
 * @Description RedisDictCache
 **/
@RequiredArgsConstructor
public class RedisDictCache implements DictCacheStrategy {

    private final RedisTemplate<String, Map<String, DictDataEntity>> redisTemplate;
    public static final String REDIS_DICT_CACHE_KEY = "xcode:dict:cache:";

    @Override
    public void saveOrUpdateDictData(DictDataEntity dictDataEntity) {
        Map<String, DictDataEntity> map = redisTemplate.opsForValue().get(REDIS_DICT_CACHE_KEY + dictDataEntity.getDictType());
        if (MapUtil.isEmpty(map)) {
            map = new HashMap<>();
            map.put(dictDataEntity.getCode(), dictDataEntity);
            redisTemplate.opsForValue().set(REDIS_DICT_CACHE_KEY + dictDataEntity.getDictType(), map);
        } else {
            DictDataEntity dataEntity = map.getOrDefault(dictDataEntity.getCode(), null);
            if (Objects.isNull(dataEntity)){
                map.put(dictDataEntity.getCode(), dictDataEntity);
            }else {
                String name = dictDataEntity.getName();
                String desc = dictDataEntity.getDesc();
                dataEntity.setName(name);
                if (StrUtil.isNotBlank(desc)){
                    dataEntity.setDesc(desc);
                }
                map.put(dictDataEntity.getCode(), dataEntity);
            }
            redisTemplate.opsForValue().set(REDIS_DICT_CACHE_KEY + dictDataEntity.getDictType(), map);
        }
    }

    @Override
    public void removeDictData(String dictCode, String dictType) {
        Map<String, DictDataEntity> map = redisTemplate.opsForValue().get(REDIS_DICT_CACHE_KEY + dictType);
        if (!MapUtil.isEmpty(map)) {
            map.remove(dictCode);
            redisTemplate.opsForValue().set(REDIS_DICT_CACHE_KEY + dictType, map);
        }
    }

    @Override
    public DictDataEntity getDictData(String dictCode, String dictType) {
        Map<String, DictDataEntity> map = redisTemplate.opsForValue().get(REDIS_DICT_CACHE_KEY + dictType);
        if (!MapUtil.isEmpty(map)) {
            return map.getOrDefault(dictCode, null);
        }
        return null;
    }

    @Override
    public List<DictDataEntity> getDictTypeList(String dictType) {
        Map<String, DictDataEntity> map = redisTemplate.opsForValue().get(REDIS_DICT_CACHE_KEY + dictType);
        if (!MapUtil.isEmpty(map)) {
            return CollectionUtil.convertList(map.values(), Function.identity());
        }
        return Collections.emptyList();
    }

    @Override
    public void clearCache() {
        redisTemplate.delete(REDIS_DICT_CACHE_KEY + "*");
    }

    @Override
    public boolean containsKey(String dictType) {
        return redisTemplate.hasKey(REDIS_DICT_CACHE_KEY + dictType);
    }

    @Override
    public void removeDictType(String dictType) {
        redisTemplate.delete(REDIS_DICT_CACHE_KEY + dictType);
    }
}
