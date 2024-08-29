package cn.xk.xcode.config;

import cn.xk.xcode.enums.CheckCodeCacheType;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.enums.CheckCodeCacheType.REDIS;
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
        Map<String, CheckCodeSendTypeConfig> configMap = checkCodeProperties.getSendType();
        CheckCodeSendTypeConfig checkCodeSendTypeConfig = configMap.values().stream().findFirst().orElse(null);
        if (Objects.isNull(checkCodeSendTypeConfig)){
            throw new ServiceException(CHECK_CODE_MOBILE_NOT_CONFIG);
        }
        CheckCodeMobileProperties mobileProperties = checkCodeSendTypeConfig.getMobileConfig();
        if (Objects.isNull(mobileProperties)) {
            throw new ServiceException(CHECK_CODE_MOBILE_NOT_CONFIG);
        }
        return DefaultProfile.getProfile(mobileProperties.getRegionId(), mobileProperties.getAccessKeyId(), mobileProperties.getSecret());
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        Map<String, CheckCodeSendTypeConfig> configMap = checkCodeProperties.getSendType();
        CheckCodeSendTypeConfig checkCodeSendTypeConfig = configMap.values().stream().findFirst().orElse(null);
        JavaMailSenderImpl mailSender = getJavaMailSender(checkCodeSendTypeConfig);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        return mailSender;
    }

    private static JavaMailSenderImpl getJavaMailSender(CheckCodeSendTypeConfig checkCodeSendTypeConfig) {
        if (Objects.isNull(checkCodeSendTypeConfig)) {
            throw new ServiceException(CHECK_CODE_EMAIL_NOT_CONFIG);
        }
        CheckCodeEmailProperties emailProperties = checkCodeSendTypeConfig.getEmailConfig();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setPassword(emailProperties.getPassword());
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
        Map<String, CheckCodeSendTypeConfig> configMap = checkCodeProperties.getSendType();
        CheckCodeSendTypeConfig checkCodeSendTypeConfig = configMap.values().stream().findFirst().orElse(null);
        if (Objects.isNull(checkCodeSendTypeConfig)) {
            throw new ServiceException(CHECK_CODE_MOBILE_NOT_CONFIG);
        }
        CheckCodeMobileProperties checkCodeSendTypeConfigPhone = checkCodeSendTypeConfig.getMobileConfig();
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
