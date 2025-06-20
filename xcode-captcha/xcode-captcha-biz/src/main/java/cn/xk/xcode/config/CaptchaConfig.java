package cn.xk.xcode.config;

import cn.hutool.captcha.*;
import cn.xk.xcode.enums.CaptchaCacheType;
import cn.xk.xcode.enums.CaptchaCategory;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import cn.xk.xcode.handler.core.CaptchaAdvisor;
import cn.xk.xcode.handler.core.EmailCaptchaHandler;
import cn.xk.xcode.handler.core.MobileCaptchaHandler;
import cn.xk.xcode.handler.core.PicCaptchaHandler;
import cn.xk.xcode.handler.core.cache.InMemorySaveCaptchaCache;
import cn.xk.xcode.handler.core.cache.RedisSaveCaptchaCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.enums.CaptchaCacheType.REDIS;
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
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public SaveCaptchaCacheStrategy saveCaptchaStrategy(Cache<String, String> cache) {
        String cacheType = captchaProperties.getCacheType();
        if (!CaptchaCacheType.isValid(cacheType)) {
            throw new ServerException(CACHE_TYPE_INVALID);
        }
        if (REDIS.getType().equals(cacheType)) {
            return new RedisSaveCaptchaCache(stringRedisTemplate, captchaProperties);
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
    public PicCaptchaHandler picCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy,
                                               CaptchaProperties CaptchaProperties,
                                               AbstractCaptcha abstractCaptcha) {
        return new PicCaptchaHandler(saveCaptchaCacheStrategy, CaptchaProperties, abstractCaptcha);
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
    public EmailCaptchaHandler emailCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy, CaptchaProperties captchaProperties) {
        return new EmailCaptchaHandler(saveCaptchaCacheStrategy, captchaProperties);
    }

    @Bean
    public MobileCaptchaHandler mobileCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaCacheStrategy, CaptchaProperties captchaProperties) {
        return new MobileCaptchaHandler(saveCaptchaCacheStrategy, captchaProperties);
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
