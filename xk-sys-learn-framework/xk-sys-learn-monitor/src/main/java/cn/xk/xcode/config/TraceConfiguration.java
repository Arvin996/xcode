package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.BizTraceAdvisor;
import cn.xk.xcode.core.interceptor.BizTraceInterceptor;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;
import org.springframework.context.annotation.Bean;

/**
 * @Author xuk
 * @Date 2024/8/6 11:02
 * @Version 1.0E
 * @Description TraceConfig
 */
public class TraceConfiguration {

    @Bean
    public BizTraceAdvisor bizTraceAdvisor(Tracer tracer){
        return new BizTraceAdvisor(new BizTraceInterceptor(tracer));
    }

    @Bean
    public Tracer tracer(){
        SkywalkingTracer tracer = new SkywalkingTracer();
        GlobalTracer.registerIfAbsent(tracer);
        return tracer;
    }
}
