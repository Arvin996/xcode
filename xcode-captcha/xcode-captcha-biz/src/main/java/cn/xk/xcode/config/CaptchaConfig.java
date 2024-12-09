package cn.xk.xcode.config;

import cn.hutool.captcha.*;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.enums.CaptchaCacheType;
import cn.xk.xcode.enums.CaptchaCategory;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import cn.xk.xcode.handler.core.CaptchaAdvisor;
import cn.xk.xcode.handler.core.EmailCaptchaHandler;
import cn.xk.xcode.handler.core.MobileCaptchaHandler;
import cn.xk.xcode.handler.core.PicCaptchaHandler;
import cn.xk.xcode.handler.core.cache.InMemorySaveCaptchaCache;
import cn.xk.xcode.handler.core.cache.RedisSaveCaptchaCache;
import com.aliyuncs.profile.DefaultProfile;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.enums.CaptchaCacheType.REDIS;
import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.EMAIL_CONFIG_PROPERTY_IS_NULL;
import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.MOBILE_CONFIG_PROPERTY_IS_NULL;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/7/31 20:21
 * @Version 1.0
 * @Description CaptchaConfig
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfig {
    @Resource
    private CaptchaProperties captchaProperties;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Bean
    public SaveCaptchaCacheStrategy saveCaptchaStrategy(Cache<String, String> cache) {
        String cacheType = captchaProperties.getCacheType();
        if (!CaptchaCacheType.isValid(cacheType)) {
            throw new ServerException(CACHE_TYPE_INVALID);
        }
        if (REDIS.getType().equals(cacheType)) {
            return new RedisSaveCaptchaCache(redisTemplate, captchaProperties.getExpiredTime());
        }
        return new InMemorySaveCaptchaCache(cache);
    }

    @Bean
    public Cache<String, String> cache() {
        return Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(300)
                .expireAfterWrite(captchaProperties.getExpiredTime(), TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public DefaultProfile defaultProfile() {
        CaptchaProperties.CaptchaMobileProperties mobileProperties = captchaProperties.getMobile();
        if (ObjectUtil.isNull(mobileProperties)) {
            ExceptionUtil.castServerException(CHECK_CODE_MOBILE_NOT_CONFIG);
        }
        if (!StringUtils.hasText(mobileProperties.getSignName())) {
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "signName");
        }
        if (!StringUtils.hasText(mobileProperties.getAccessKeyId())) {
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "accessKeyId");
        }
        if (!StringUtils.hasText(mobileProperties.getSecret())) {
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "secret");
        }
        if (!StringUtils.hasText(mobileProperties.getRegionId())) {
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "regionId");
        }
        return DefaultProfile.getProfile(mobileProperties.getRegionId(), mobileProperties.getAccessKeyId(), mobileProperties.getSecret());
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        CaptchaProperties.CaptchaEmailProperties CaptchaEmailProperties = captchaProperties.getEmail();
        JavaMailSenderImpl mailSender = getJavaMailSender(CaptchaEmailProperties);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        return mailSender;
    }

    private static JavaMailSenderImpl getJavaMailSender(CaptchaProperties.CaptchaEmailProperties CaptchaEmailProperties) {
        if (Objects.isNull(CaptchaEmailProperties)) {
            ExceptionUtil.castServerException(CHECK_CODE_EMAIL_NOT_CONFIG);
        }
        if (!StringUtils.hasText(CaptchaEmailProperties.getUsername())) {
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "username");
        }
        if (!StringUtils.hasText(CaptchaEmailProperties.getHost())) {
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "host");
        }
        if (!StringUtils.hasText(CaptchaEmailProperties.getPassword())) {
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "password");
        }
        if (ObjectUtil.isNull(CaptchaEmailProperties.getPort())) {
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "port");
        }
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(CaptchaEmailProperties.getUsername());
        mailSender.setHost(CaptchaEmailProperties.getHost());
        mailSender.setPort(CaptchaEmailProperties.getPort());
        mailSender.setPassword(CaptchaEmailProperties.getPassword());
        return mailSender;
    }

    @Bean
    public PicCaptchaHandler picCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy,
                                               CaptchaProperties CaptchaProperties,
                                               AbstractCaptcha abstractCaptcha) {
        return new PicCaptchaHandler(saveCaptchaCacheStrategy, CaptchaProperties, abstractCaptcha);
    }

    @Bean
    public EmailCaptchaHandler emailCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy,
                                                   CaptchaProperties CaptchaProperties,
                                                   JavaMailSenderImpl javaMailSender) {
        return new EmailCaptchaHandler(saveCaptchaCacheStrategy, CaptchaProperties, javaMailSender, CaptchaProperties.getExpiredTime());
    }

    @Bean
    public MobileCaptchaHandler mobileCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy,
                                                     CaptchaProperties CaptchaProperties,
                                                     DefaultProfile defaultProfile) {
        CaptchaProperties.CaptchaMobileProperties CaptchaSendTypeConfigPhone = CaptchaProperties.getMobile();
        String signName = CaptchaSendTypeConfigPhone.getSignName();
        return new MobileCaptchaHandler(saveCaptchaCacheStrategy, CaptchaProperties, defaultProfile, signName, CaptchaProperties.getExpiredTime());
    }

    @Bean
    public CaptchaAdvisor CaptchaAdvisor(List<CaptchaHandlerStrategy> list) {
        Map<String, CaptchaHandlerStrategy> CaptchaHandlerStrategyMap = new HashMap<>();
        for (CaptchaHandlerStrategy CaptchaHandlerStrategy : list) {
            CaptchaHandlerStrategyMap.put(CaptchaHandlerStrategy.getType().getCode(), CaptchaHandlerStrategy);
        }
        return new CaptchaAdvisor(CaptchaHandlerStrategyMap);
    }

    @Bean
    public AbstractCaptcha abstractCaptcha() {
        CaptchaCategory category = captchaProperties.getPic().getCategory();
        if (category.equals(CaptchaCategory.LINE)) {
            return lineCaptcha();
        } else if (category.equals(CaptchaCategory.SHEAR)) {
            return shearCaptcha();
        } else {
            return circleCaptcha();
        }
    }

    /**
     * 圆圈干扰验证码
     */
    public CircleCaptcha circleCaptcha() {
        CaptchaProperties.CaptchaPicProperties captchaPropertiesPic = captchaProperties.getPic();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(captchaPropertiesPic.getWidth(), captchaPropertiesPic.getHeight());
        captcha.setBackground(captchaPropertiesPic.getBackground());
        captcha.setFont(CaptchaProperties.FONT);
        return captcha;
    }

    /**
     * 线段干扰的验证码
     */
    public LineCaptcha lineCaptcha() {
        CaptchaProperties.CaptchaPicProperties captchaPropertiesPic = captchaProperties.getPic();
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(captchaPropertiesPic.getWidth(), captchaPropertiesPic.getHeight());
        captcha.setBackground(captchaPropertiesPic.getBackground());
        captcha.setFont(CaptchaProperties.FONT);
        return captcha;
    }

    /**
     * 扭曲干扰验证码
     */
    public ShearCaptcha shearCaptcha() {
        CaptchaProperties.CaptchaPicProperties captchaPropertiesPic = captchaProperties.getPic();
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(captchaPropertiesPic.getWidth(), captchaPropertiesPic.getHeight());
        captcha.setBackground(captchaPropertiesPic.getBackground());
        captcha.setFont(CaptchaProperties.FONT);
        return captcha;
    }

}
