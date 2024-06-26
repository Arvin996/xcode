package cn.xk.xcode.config;

import cn.xk.xcode.context.DictContext;
import cn.xk.xcode.handler.DataBaseDictHandler;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xuk
 * @Date 2024/6/21 10:00
 * @Version 1.0
 * @Description DictConfig
 */
@Configuration
public class DictConfig
{
    @Bean
    public FeignClientBuilder feignClientBuilder(ApplicationContext applicationContext){
        return new FeignClientBuilder(applicationContext);
    }

    @Bean
    public DataBaseDictHandler dataBaseDictHandler(){
        return new DataBaseDictHandler();
    }

    @Bean
    public DictContext dictContext(DataBaseDictHandler dataBaseDictHandler){
        return new DictContext(dataBaseDictHandler);
    }
}
