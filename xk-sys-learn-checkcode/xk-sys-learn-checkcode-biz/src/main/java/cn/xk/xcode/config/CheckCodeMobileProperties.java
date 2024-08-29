package cn.xk.xcode.config;

import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/8/1 11:19
 * @Version 1.0
 * @Description PhoneConfig
 */
@Data
public class CheckCodeMobileProperties {
    private String regionId;

    private String accessKeyId;

    private String secret;

    private String signName;
}
