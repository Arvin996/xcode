package cn.xk.xcode.config;

import cn.xk.xcode.context.DictContext;
import cn.xk.xcode.handler.DataBaseDictHandler;
import cn.xk.xcode.handler.EnumDictHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/6/21 10:00
 * @Version 1.0
 * @Description DictConfig
 */
@EnableConfigurationProperties(DictEnumPackages.class)
@Configuration
public class DictConfig {

    @Resource
    private DictEnumPackages dictEnumPackages;

    @Bean
    public FeignClientBuilder feignClientBuilder(ApplicationContext applicationContext){
        return new FeignClientBuilder(applicationContext);
    }
    
    @Bean
    public DataBaseDictHandler dataBaseDictHandler(){
        return new DataBaseDictHandler();
    }

    @Bean
    public EnumDictHandler enumDictHandler(){
        return new EnumDictHandler(dictEnumPackages);
    }
}
