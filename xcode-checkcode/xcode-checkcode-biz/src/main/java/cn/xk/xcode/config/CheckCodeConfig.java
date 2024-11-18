package cn.xk.xcode.config;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.enums.CheckCodeCacheType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import cn.xk.xcode.handler.core.CheckCodeAdvisor;
import cn.xk.xcode.handler.core.EmailCheckCodeHandler;
import cn.xk.xcode.handler.core.MobileCheckCodeHandler;
import cn.xk.xcode.handler.core.PicCheckCodeHandler;
import cn.xk.xcode.handler.core.cache.InMemorySaveCodeCache;
import cn.xk.xcode.handler.core.cache.RedisSaveCodeCache;
import com.aliyuncs.profile.DefaultProfile;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.enums.CheckCodeCacheType.REDIS;
import static cn.xk.xcode.enums.CheckCodeGlobalErrorCodeConstants.EMAIL_CONFIG_PROPERTY_IS_NULL;
import static cn.xk.xcode.enums.CheckCodeGlobalErrorCodeConstants.MOBILE_CONFIG_PROPERTY_IS_NULL;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/7/31 20:21
 * @Version 1.0
 * @Description CheckCodeConfig
 */
@Configuration
@EnableConfigurationProperties(CheckCodeProperties.class)
public class CheckCodeConfig {
    @Resource
    private CheckCodeProperties checkCodeProperties;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Bean
    public SaveCheckCodeCacheStrategy saveCheckCodeStrategy(Cache<String, String> cache) {
        String cacheType = checkCodeProperties.getCacheType();
        if (!CheckCodeCacheType.isValid(cacheType)) {
            throw new ServerException(CACHE_TYPE_INVALID);
        }
        if (REDIS.getType().equals(cacheType)) {
            return new RedisSaveCodeCache(redisTemplate, checkCodeProperties.getExpiredTime());
        }
        return new InMemorySaveCodeCache(cache);
    }

    @Bean
    public Cache<String, String> cache() {
        return Caffeine.newBuilder()
                .initialCapacity(200)
                .maximumSize(300)
                .expireAfterWrite(checkCodeProperties.getExpiredTime(), TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public DefaultProfile defaultProfile() {
        CheckCodeProperties.CheckCodeMobileProperties mobileProperties = checkCodeProperties.getMobile();
        if (ObjectUtil.isNull(mobileProperties)){
            ExceptionUtil.castServerException(CHECK_CODE_MOBILE_NOT_CONFIG);
        }
        if (!StringUtils.hasText(mobileProperties.getSignName())){
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "signName");
        }
        if (!StringUtils.hasText(mobileProperties.getAccessKeyId())){
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "accessKeyId");
        }
        if (!StringUtils.hasText(mobileProperties.getSecret())){
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "secret");
        }
        if (!StringUtils.hasText(mobileProperties.getRegionId())){
            ExceptionUtil.castServerException(MOBILE_CONFIG_PROPERTY_IS_NULL, "regionId");
        }
        return DefaultProfile.getProfile(mobileProperties.getRegionId(), mobileProperties.getAccessKeyId(), mobileProperties.getSecret());
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        CheckCodeProperties.CheckCodeEmailProperties checkCodeEmailProperties = checkCodeProperties.getEmail();
        JavaMailSenderImpl mailSender = getJavaMailSender(checkCodeEmailProperties);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        return mailSender;
    }

    private static JavaMailSenderImpl getJavaMailSender(CheckCodeProperties.CheckCodeEmailProperties checkCodeEmailProperties) {
        if (Objects.isNull(checkCodeEmailProperties)) {
           ExceptionUtil.castServerException(CHECK_CODE_EMAIL_NOT_CONFIG);
        }
        if (!StringUtils.hasText(checkCodeEmailProperties.getUsername())){
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "username");
        }
        if (!StringUtils.hasText(checkCodeEmailProperties.getHost())){
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "host");
        }
        if (!StringUtils.hasText(checkCodeEmailProperties.getPassword())){
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "password");
        }
        if (ObjectUtil.isNull(checkCodeEmailProperties.getPort())){
            ExceptionUtil.castServerException(EMAIL_CONFIG_PROPERTY_IS_NULL, "port");
        }
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(checkCodeEmailProperties.getUsername());
        mailSender.setHost(checkCodeEmailProperties.getHost());
        mailSender.setPort(checkCodeEmailProperties.getPort());
        mailSender.setPassword(checkCodeEmailProperties.getPassword());
        return mailSender;
    }

    @Bean
    public PicCheckCodeHandler picCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeCacheStrategy,
                                                   CheckCodeProperties checkCodeProperties,
                                                   DefaultKaptcha defaultKaptcha){
        return new PicCheckCodeHandler(saveCheckCodeCacheStrategy, checkCodeProperties, defaultKaptcha);
    }

    @Bean
    public EmailCheckCodeHandler emailCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeCacheStrategy,
                                                       CheckCodeProperties checkCodeProperties,
                                                       JavaMailSenderImpl javaMailSender){
        return new EmailCheckCodeHandler(saveCheckCodeCacheStrategy, checkCodeProperties, javaMailSender, checkCodeProperties.getExpiredTime());
    }

    @Bean
    public MobileCheckCodeHandler mobileCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeCacheStrategy,
                                                                         CheckCodeProperties checkCodeProperties,
                                                                         DefaultProfile defaultProfile){
        CheckCodeProperties.CheckCodeMobileProperties checkCodeSendTypeConfigPhone = checkCodeProperties.getMobile();
        String signName = checkCodeSendTypeConfigPhone.getSignName();
        return new MobileCheckCodeHandler(saveCheckCodeCacheStrategy, checkCodeProperties, defaultProfile, signName, checkCodeProperties.getExpiredTime());
    }

    @Bean
    public CheckCodeAdvisor checkCodeAdvisor(List<CheckCodeHandlerStrategy> list){
        Map<String, CheckCodeHandlerStrategy> checkCodeHandlerStrategyMap = new HashMap<>();
        for (CheckCodeHandlerStrategy checkCodeHandlerStrategy : list) {
            checkCodeHandlerStrategyMap.put(checkCodeHandlerStrategy.getType().getCode(), checkCodeHandlerStrategy);
        }
        return new CheckCodeAdvisor(checkCodeHandlerStrategyMap);
    }

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "10");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.image.height","34");
        properties.put("kaptcha.image.width","138");
        properties.put("kaptcha.textproducer.font.size","25");
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
