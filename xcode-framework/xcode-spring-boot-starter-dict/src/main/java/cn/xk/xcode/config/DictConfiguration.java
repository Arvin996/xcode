package cn.xk.xcode.config;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.aop.DictRefreshAspect;
import cn.xk.xcode.aop.DictTranAspect;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.cache.InMemoryDictCache;
import cn.xk.xcode.cache.RedisDictCache;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.event.DictDataStartLoadRunner;
import cn.xk.xcode.handler.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/6/21 10:00
 * @Version 1.0
 * @Description DictConfig
 */
@Configuration
@ConditionalOnProperty(value = "xcode.dict.enable", havingValue = "true")
public class DictConfiguration {

    @Resource
    private XcodeDictProperties xcodeDictProperties;

    @Bean
    public FeignClientBuilder feignClientBuilder(ApplicationContext applicationContext) {
        return new FeignClientBuilder(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(DataSourceDictLoader.class)
    public DataSourceDictLoader dataSourceDictLoader() {
        return new DefaultDataSourceLoader();
    }

    @Bean
    public DictCacheStrategy dictCacheStrategy() {
        if (xcodeDictProperties.getCacheType().equals(XcodeDictProperties.CacheType.REDIS)) {
            return new RedisDictCache(SpringUtil.getBean("dictRedisDictCache"));
        } else {
            return new InMemoryDictCache();
        }
    }

    @Bean("dictRedisDictCache")
    public RedisTemplate<String, Map<String, DictDataEntity>> dictRedisDictCache(RedisConnectionFactory redisConnectionFactory) {
        if (xcodeDictProperties.getCacheType().equals(XcodeDictProperties.CacheType.REDIS)) {
            RedisTemplate<String, Map<String, DictDataEntity>> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(jackson2JsonRedisSerializer);
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(jackson2JsonRedisSerializer);
            template.afterPropertiesSet();
            return template;
        } else {
            return null;
        }
    }

    @Bean
    public DataSourceDictHandler dataSourceDictHandler(DataSourceDictLoader dataSourceDictLoader, DictCacheStrategy dictCacheStrategy) {
        return new DataSourceDictHandler(dictCacheStrategy, dataSourceDictLoader);
    }

    @Bean
    public EnumDictHandler enumDictHandler(DictCacheStrategy dictCacheStrategy) {
        return new EnumDictHandler(xcodeDictProperties, dictCacheStrategy);
    }

    @Bean
    public RpcTransInnerController rpcTransInnerController(DictCacheStrategy dictCacheStrategy) {
        return new RpcTransInnerController(dictCacheStrategy);
    }

    @Bean
    public DictDataStartLoadRunner dictDataStartLoadRunner() {
        return new DictDataStartLoadRunner();
    }

    @Bean
    public DictTranAspect dictTranAspect(FeignClientBuilder feignClientBuilder, DictCacheStrategy dictCacheStrategy) {
        return new DictTranAspect(feignClientBuilder, dictCacheStrategy);
    }

    @Bean
    public DictRefreshAspect dictRefreshAspect(DictCacheStrategy dictCacheStrategy) {
        return new DictRefreshAspect(dictCacheStrategy);
    }

}
