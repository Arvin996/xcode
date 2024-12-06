package cn.xk.xcode.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/11/22 15:59
 * @Version 1.0.0
 * @Description AbstractRepeatedFilter
 **/
public abstract class AbstractRepeatedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest
                && StringUtils.startsWithIgnoreCase(servletRequest.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) servletRequest, servletResponse);
        }
        if (null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            handlerBody(requestWrapper, servletRequest, servletResponse);
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public abstract void handlerBody(ServletRequest requestWrapper, ServletRequest servletRequest, ServletResponse servletResponse);
}
