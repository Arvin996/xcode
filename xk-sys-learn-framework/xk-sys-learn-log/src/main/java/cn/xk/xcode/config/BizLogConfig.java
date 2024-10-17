package cn.xk.xcode.config;

import cn.xk.xcode.aop.BizLogAnnotationAdvisor;
import cn.xk.xcode.aop.BizLogAnnotationInterceptor;
import cn.xk.xcode.aop.UserBizLogMdcAdvisor;
import cn.xk.xcode.aop.UserBizLogMdcInterceptor;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.support.LogValueParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/7/4 15:04
 * @Version 1.0
 * @Description BizLogConfig
 */
@Configuration
public class BizLogConfig
{
    @Resource
    private LogValueParser logValueParser;

    @Resource
    private RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    @Bean
    public BizLogAnnotationInterceptor bizLogAnnotationInterceptor(){
        return new BizLogAnnotationInterceptor(logValueParser, rocketMQEnhanceTemplate);
    }

    @Bean
    public BizLogAnnotationAdvisor bizLogAnnotationAdvisor(BizLogAnnotationInterceptor bizLogAnnotationInterceptor){
        return new BizLogAnnotationAdvisor(bizLogAnnotationInterceptor);
    }

    @Bean
    public UserBizLogMdcInterceptor userBizLogMdcInterceptor(){
        return new UserBizLogMdcInterceptor();
    }

    @Bean
    public UserBizLogMdcAdvisor userBizLogMdcAdvisor(UserBizLogMdcInterceptor userBizLogMdcInterceptor){
        return new UserBizLogMdcAdvisor(userBizLogMdcInterceptor);
    }
}
