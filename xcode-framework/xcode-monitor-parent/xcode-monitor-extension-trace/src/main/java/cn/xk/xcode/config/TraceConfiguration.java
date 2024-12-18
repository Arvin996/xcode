package cn.xk.xcode.config;

import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.core.aop.GlobalTraceAop;
import cn.xk.xcode.core.interceptor.RpcHttpRequestInterceptor;
import cn.xk.xcode.core.interceptor.TraceWebHttpRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author xuk
 * @Date 2024/12/18 15:28
 * @Version 1.0.0
 * @Description TraceConfiguration
 **/
public class TraceConfiguration implements WebMvcConfigurer {

    @Bean
    public GlobalTraceAop globalTraceAop(RocketMQEnhanceTemplate rocketMQEnhanceTemplate){
        return new GlobalTraceAop(rocketMQEnhanceTemplate);
    }

    @Bean
    public RpcHttpRequestInterceptor rpcHttpRequestInterceptor(){
        return new RpcHttpRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceWebHttpRequestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/doLogin", "/webjars/**", "/doc.html", "/v3/**", "/warm-flow-ui/**", "/warm-flow/**")
                .order(Integer.MAX_VALUE);
    }
}
