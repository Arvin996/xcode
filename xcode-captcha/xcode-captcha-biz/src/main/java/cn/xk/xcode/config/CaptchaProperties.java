package cn.xk.xcode.config;

import cn.xk.xcode.enums.CaptchaCategory;
import cn.xk.xcode.enums.PicCaptchaType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.awt.*;
import java.util.Map;

import static cn.xk.xcode.enums.CaptchaCacheType.LOCAL;

/**
 * @Author xuk
 * @Date 2024/7/31 20:18
 * @Version 1.0
 * @Description CaptchaProperties
 */
@Data
@ConfigurationProperties("xcode.captcha")
public class CaptchaProperties {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 60;
    private static final Color BACKGROUND = Color.PINK;
    public static final Font FONT = new Font("Arial", Font.BOLD, 48);

    private String cacheType = LOCAL.getType();
    private Integer expiredTime = 60;
    private CaptchaEmailProperties email;
    private CaptchaMobileProperties mobile;
    private CaptchaPicProperties pic;

    @Data
    public static class CaptchaPicProperties {
        private CaptchaCategory category = CaptchaCategory.LINE;
        private PicCaptchaType type = PicCaptchaType.CHAR;
        private Integer charLength = 4;
        private Integer mathLength = 1;
        private Integer width = WIDTH;
        private Integer height = HEIGHT;
        private Color background = BACKGROUND;
    }


    @Getter
    @Setter
    public static class CaptchaEmailProperties {

        private Integer length = 4;
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
    public static class CaptchaMobileProperties {
        private Integer length = 4;
        private String regionId;
        private String accessKeyId;
        private String secret;
        private String signName;
    }
}
