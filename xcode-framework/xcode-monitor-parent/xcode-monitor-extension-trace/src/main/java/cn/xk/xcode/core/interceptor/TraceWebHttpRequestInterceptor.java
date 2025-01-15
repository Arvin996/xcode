package cn.xk.xcode.core.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.context.TraceContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.xk.xcode.core.context.TraceContextHolder.*;

/**
 * @Author xuk
 * @Date 2024/12/18 10:44
 * @Version 1.0.0
 * @Description TraceHttpRequestInterceptor
 **/
public class TraceWebHttpRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        TraceContextHolder.setRequestUrl(request.getRequestURL().toString());
        String traceId = request.getHeader(TRACE_ID_KEY);
        String traceSort = request.getHeader(TRACE_SORT_KEY);
        String callService = request.getHeader(CALL_SERVICE_KEY);
        if (StrUtil.isNotEmpty(traceId)){
            TraceContextHolder.setTraceId(traceId);
        }
        if (StrUtil.isNotEmpty(traceSort)){
            TraceContextHolder.setTraceSort(Integer.valueOf(traceSort));
        }else {
            TraceContextHolder.setTraceSort(1);
        }
        if (StrUtil.isNotEmpty(callService)){
            TraceContextHolder.setCallService(callService);
        }else {
            // 支付宝或者微信等三方服务调用
            TraceContextHolder.setCallService("三方服务");
        }
        return true;
    }
}
