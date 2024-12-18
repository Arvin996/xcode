package cn.xk.xcode.core.interceptor;

import cn.xk.xcode.core.context.TraceContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

import static cn.xk.xcode.core.context.TraceContextHolder.*;

/**
 * @Author xuk
 * @Date 2024/12/18 10:58
 * @Version 1.0.0
 * @Description RpcHttpRequestInterceptor
 **/
public class RpcHttpRequestInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TRACE_ID_KEY, TraceContextHolder.getTraceId());
        requestTemplate.header(CALL_SERVICE_KEY, serviceName);
        Integer traceSort = getTraceSort();
        traceSort = traceSort + 1;
        requestTemplate.header(TRACE_SORT_KEY, traceSort.toString());
    }
}
