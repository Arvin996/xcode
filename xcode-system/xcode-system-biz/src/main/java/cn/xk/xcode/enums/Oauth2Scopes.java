package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/2/10 10:26
 * @Version 1.0.0
 * @Description Oauth2Scopes
 **/
@Getter
@AllArgsConstructor
public enum Oauth2Scopes {

    USER_INFO("userinfo");

    private final String value;

}
