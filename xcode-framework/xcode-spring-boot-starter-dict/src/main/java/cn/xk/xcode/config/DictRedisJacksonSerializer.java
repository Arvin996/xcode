package cn.xk.xcode.config;

import cn.xk.xcode.entity.DictDataEntity;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/27 9:52
 * @Version 1.0.0
 * @Description DictRedisJacksonSerializer
 **/
@SuppressWarnings("all")
public class DictRedisJacksonSerializer extends GenericJackson2JsonRedisSerializer {

    public DictRedisJacksonSerializer() {
        super();
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null){
            return null;
        }
        Map<String, DictDataEntity> map = (Map<String, DictDataEntity>) source;
        return JSONObject.toJSONString(map).getBytes();
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        if (source == null) {
            return null;
        }
        String string = new String(source);
        JSONObject map = JSONObject.parseObject(string);
        Map newMap = new HashMap();
        for (String s : map.keySet()) {
            JSONObject m = map.getJSONObject(s);
            DictDataEntity dictDataEntity = new DictDataEntity();
            dictDataEntity.setCode(m.get("code").toString());
            dictDataEntity.setName(m.get("name").toString());
            dictDataEntity.setDictType(m.get("dictType").toString());
            dictDataEntity.setDesc(m.getString("desc"));
            newMap.put(s, dictDataEntity);
        }
        return newMap;
    }

    @Override
    public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {
        // 反序列化为Map<String, DictDataEntity>
        if (source == null){
            return null;
        }
        String string = new String(source);
        JSONObject map = JSONObject.parseObject(string);
        Map newMap = new HashMap();
        map.forEach((k, v) -> {
            if (v instanceof Map) {
                Map m = (Map) v;
                DictDataEntity dictDataEntity = new DictDataEntity();
                dictDataEntity.setCode(m.get("code").toString());
                dictDataEntity.setName(m.get("name").toString());
                dictDataEntity.setDictType(m.get("dictType").toString());
                dictDataEntity.setDesc(m.get("desc").toString());
                newMap.put(k, dictDataEntity);
            }
        });
        return (T) newMap;

    }
}
