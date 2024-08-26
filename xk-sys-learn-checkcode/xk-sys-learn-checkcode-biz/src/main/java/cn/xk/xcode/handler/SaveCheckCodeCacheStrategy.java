package cn.xk.xcode.handler;

/**
 * @Author xuk
 * @Date 2024/7/31 19:14
 * @Version 1.0
 * @Description SaveCheckCodeStrategy
 */
public interface SaveCheckCodeCacheStrategy {

    void save(String code);

    void remove(String code);

    String get(String code);
}
