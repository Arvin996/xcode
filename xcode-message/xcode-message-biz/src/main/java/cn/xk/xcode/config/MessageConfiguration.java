package cn.xk.xcode.config;

import cn.xk.xcode.balance.core.FirstLoadBalancer;
import cn.xk.xcode.balance.core.ILoadBalancer;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.handler.message.IHandler;
import cn.xk.xcode.limit.BaseRateLimiter;
import cn.xk.xcode.limit.GlobalRequestRateLimiter;
import cn.xk.xcode.limit.RateLimiterHolder;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/5 17:11
 * @Version 1.0.0
 * @Description MessageThreadPoolConfiguration
 **/
@Configuration
@EnableConfigurationProperties({XcodeMessageProperties.class})
public class MessageConfiguration {

    // 配置默认敏感词 + 自定义敏感词
    IWordDeny wordDeny = WordDenys.chains(WordDenys.defaults(), new CustomWordDeny());
    // 配置默认非敏感词 + 自定义非敏感词
    IWordAllow wordAllow = WordAllows.chains(WordAllows.defaults(), new CustomWordAllow());


    @Bean
    public MessageHandlerHolder messageHandlerHolder(List<IHandler> handlers) {
        return new MessageHandlerHolder(handlers);
    }

    @Bean
    public BaseRateLimiter globalRequestRateLimiter(RedissonClient redissonClient, XcodeMessageProperties xcodeMessageProperties) {
        return new GlobalRequestRateLimiter(redissonClient, xcodeMessageProperties);
    }

    @Bean
    public BaseRateLimiter channelSendMsgRateLimiter(RedissonClient redissonClient, XcodeMessageProperties xcodeMessageProperties) {
        return new GlobalRequestRateLimiter(redissonClient, xcodeMessageProperties);
    }

    @Bean
    public RateLimiterHolder rateLimiterHolder(List<BaseRateLimiter> rateLimiters) {
        return new RateLimiterHolder(rateLimiters);
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer iLoadBalancer() {
        return new FirstLoadBalancer();
    }

    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        return SensitiveWordBs.newInstance()
                // 忽略大小写
                .ignoreCase(true)
                // 忽略半角圆角
                .ignoreWidth(true)
                // 忽略数字的写法
                .ignoreNumStyle(true)
                // 忽略中文的书写格式：简繁体
                .ignoreChineseStyle(true)
                // 忽略英文的书写格式
                .ignoreEnglishStyle(true)
                // 忽略重复词
                .ignoreRepeat(false)
                // 是否启用数字检测
                .enableNumCheck(true)
                // 是否启用邮箱检测
                .enableEmailCheck(true)
                // 是否启用链接检测
                .enableUrlCheck(true)
                // 数字检测，自定义指定长度
                .numCheckLen(8)
                // 配置自定义敏感词
                .wordDeny(wordDeny)
                // 配置非自定义敏感词
                .wordAllow(wordAllow)
                .init();
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        // 设置序列化器（默认就是字符串序列化器，此处可省略）
        // template.setKeySerializer(RedisSerializer.string());
        // template.setValueSerializer(RedisSerializer.string());
        // template.setHashKeySerializer(RedisSerializer.string());
        // template.setHashValueSerializer(RedisSerializer.string());
        return new StringRedisTemplate(factory);
    }


}
