package cn.xk.xcode.handler;

/**
 * @Author xuk
 * @Date 2024/7/31 19:14
 * @Version 1.0
 * @Description SaveCaptchaStrategy
 */
public interface SaveCaptchaCacheStrategy {

    void save(String k, String v);

    void remove(String k);

    String get(String k);
}
