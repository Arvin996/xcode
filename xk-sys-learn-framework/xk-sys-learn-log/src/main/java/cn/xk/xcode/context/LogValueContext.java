package cn.xk.xcode.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/4 10:39
 * @Version 1.0
 * @Description LogValueContext
 */
public class LogValueContext {
    // 定义threadLocal存储存储现场上下文变量 放在spel context中解析
    private static final InheritableThreadLocal<Map<String, Object>> GLOBAL_VARIABLE_MAP = new InheritableThreadLocal<>();

    public static void putVariable(String name, Object value) {
        if (GLOBAL_VARIABLE_MAP.get() == null){
            GLOBAL_VARIABLE_MAP.set(new HashMap<>());
        }
        GLOBAL_VARIABLE_MAP.get().put(name, value);
    }


    public static void clearVariable() {
        if (GLOBAL_VARIABLE_MAP.get() != null) {
            (GLOBAL_VARIABLE_MAP.get()).clear();
        }

    }

    public static Map<String, Object> getVariableMap() {
        return GLOBAL_VARIABLE_MAP.get();
    }

    public static Object getVariable(String key) {
        Map<String, Object> variableMap = GLOBAL_VARIABLE_MAP.get();
        return variableMap == null ? null : variableMap.get(key);
    }

    public static void putEmptySpan(){
        if (GLOBAL_VARIABLE_MAP.get() == null){
            GLOBAL_VARIABLE_MAP.set(new HashMap<>());
        }
    }
}
