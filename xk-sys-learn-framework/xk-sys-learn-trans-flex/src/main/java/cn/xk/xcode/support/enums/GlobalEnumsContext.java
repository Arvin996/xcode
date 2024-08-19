package cn.xk.xcode.support.enums;

import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.utils.object.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.constants.TransFlexGlobalConstants.ENUM_ALREADY_EXIST;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 9:14
 * @description
 */
public class GlobalEnumsContext {
    private final TransEnumsRegistry transEnumsRegistry;
    private final Map<String, Object> enumsMap = new HashMap<>();

    public GlobalEnumsContext(TransEnumsRegistry transEnumsRegistry) {
        this.transEnumsRegistry = transEnumsRegistry;
        this.initialize();
    }

    private void initialize() {
        List<BaseEnum> enums = this.transEnumsRegistry.getEnums();
        enums.forEach(e -> {
            if (enumsMap.containsKey(e.enumType() + e.key())){
                String message = ENUM_ALREADY_EXIST.getMessage();
                message = String.format(message, e.enumType(), e.key());
                throw new ServiceException(ENUM_ALREADY_EXIST.getCode(), message);
            }
            enumsMap.put(e.enumType() + e.key(), e.value());
        });
    }

    public Object getValue(String enumType, Object key){
        return this.enumsMap.getOrDefault(enumType + key, null);
    }

    public String getValueAsString(String enumType, Object key){
        Object value = getValue(enumType, key);
        if (Objects.isNull(value)){
            return null;
        }
        return String.valueOf(value);
    }

    public Integer getValueAsInteger(String enumType, Object key){
        Object value = getValue(enumType, key);
        if (Objects.isNull(value)){
            return null;
        }
        return Integer.valueOf(String.valueOf(value));
    }

    public  <T> T getValueAsClass(String enumType, Object key, Class<T> aClass){
        Object value = getValue(enumType, key);
        if (Objects.isNull(value)){
            return null;
        }
        return BeanUtil.toBean(value, aClass);
    }

}
