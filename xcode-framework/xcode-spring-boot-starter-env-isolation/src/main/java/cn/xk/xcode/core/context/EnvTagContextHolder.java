package cn.xk.xcode.core.context;

/**
 * @Author xuk
 * @Date 2024/11/29 8:51
 * @Version 1.0.0
 * @Description EnvTagContextHolder
 **/
public class EnvTagContextHolder {

    private static final ThreadLocal<String> TAG_CONTEXT = new InheritableThreadLocal<>();

    public static void setTag(String tag) {
        TAG_CONTEXT.set(tag);
    }

    public static String getTag() {
        return TAG_CONTEXT.get();
    }

    public static void removeTag() {
        TAG_CONTEXT.remove();
    }

}
