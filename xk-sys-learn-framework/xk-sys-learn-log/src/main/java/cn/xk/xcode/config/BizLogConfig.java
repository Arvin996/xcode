package cn.xk.xcode.config;

import cn.xk.xcode.aop.BizLogAnnotationAdvisor;
import cn.xk.xcode.aop.BizLogAnnotationInterceptor;
import cn.xk.xcode.aop.UserBizLogMdcAdvisor;
import cn.xk.xcode.aop.UserBizLogMdcInterceptor;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.support.LogValueParser;
import cn.xk.xcode.support.mdc.DefaultUserBizExtraMdcSupportBase;
import cn.xk.xcode.support.mdc.UserBizExtraMdcSupportBase;
import cn.xk.xcode.support.mdc.UserBizMdcPropRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public UserBizMdcPropRegister userBizMdcPropRegister(){
        return new UserBizMdcPropRegister();
    }

    @Bean
    public UserBizLogMdcInterceptor userBizLogMdcInterceptor(UserBizMdcPropRegister userBizMdcPropRegister, List<UserBizExtraMdcSupportBase> userBizExtraMdcSupportBaseList){
        Map<Class<? extends UserBizExtraMdcSupportBase>, UserBizExtraMdcSupportBase> userBizExtraMdcSupportBaseMap = new HashMap<>();
        userBizExtraMdcSupportBaseList.forEach(v -> {
            userBizExtraMdcSupportBaseMap.put(v.getClass(), v);
        });
        return new UserBizLogMdcInterceptor(userBizMdcPropRegister, userBizExtraMdcSupportBaseMap);
    }

    @Bean
    public UserBizLogMdcAdvisor userBizLogMdcAdvisor(UserBizLogMdcInterceptor userBizLogMdcInterceptor){
        return new UserBizLogMdcAdvisor(userBizLogMdcInterceptor);
    }

    @Bean
    public DefaultUserBizExtraMdcSupportBase defaultUserBizExtraMdcSupportBase(){
        return new DefaultUserBizExtraMdcSupportBase();
    }


}
