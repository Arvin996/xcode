package cn.xk.xcode.core;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/5/14 9:34
 * @Version 1.0.0
 * @Description CheckLoginHandler
 **/
@FunctionalInterface
public interface CheckLoginHandler {
    boolean checkLogin(String token, Map<String, Object> attributes);
}
