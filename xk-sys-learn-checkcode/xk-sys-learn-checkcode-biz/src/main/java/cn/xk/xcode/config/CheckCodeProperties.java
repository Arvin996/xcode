package cn.xk.xcode.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static cn.xk.xcode.enums.CheckCodeCacheType.LOCAL;

/**
 * @Author xuk
 * @Date 2024/7/31 20:18
 * @Version 1.0
 * @Description CheckCodeProperties
 */
@Data
@ConfigurationProperties("xk.sys.checkcode")
public class CheckCodeProperties {

    private String cacheType = LOCAL.getType();

    private Integer expiredTime = 60;

    private Integer codeLength = 4;

    private CheckCodeEmailProperties email;

    private CheckCodeMobileProperties mobile;


    @Getter
    @Setter
    public static class CheckCodeEmailProperties {
        // SMTP服务器地址
        private String host;

        // SMTP服务器端口号
        private int port;

        // 登录SMTP服务器所需的用户名
        private String username;

        // 登录SMTP服务器所需的密码
        private String password;
    }

    @Getter
    @Setter
    public static class CheckCodeMobileProperties {

        private String regionId;

        private String accessKeyId;

        private String secret;

        private String signName;
    }
}
