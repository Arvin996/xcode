package cn.xk.xcode.filter;

import cn.xk.xcode.utils.TraceUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/11/13 10:33
 * @Version 1.0.0
 * @Description GlobalTraceFilter 如果使用了sky-walking 则将tracId塞到返回header中
 **/
public class GlobalTraceFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader(TRACE_ID, TraceUtil.getTraceId());
        filterChain.doFilter(request, response);
    }
}
