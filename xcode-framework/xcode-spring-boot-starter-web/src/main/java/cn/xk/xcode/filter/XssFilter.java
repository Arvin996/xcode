package cn.xk.xcode.filter;

import cn.xk.xcode.config.XcodeXssProperties;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/22 16:06
 * @Version 1.0.0
 * @Description XssFilter
 **/
@RequiredArgsConstructor
public class XssFilter implements Filter {

    /**
     * 排除链接
     */
    private final XcodeXssProperties xcodeXssProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        String method = request.getMethod();
        if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
            return true;
        }
        return isMatch(url, xcodeXssProperties.getExcludeList());
    }

    @Override
    public void destroy() {

    }

    private boolean isMatch(String pattern, List<String> urls) {
        if (CollectionUtil.isEmpty(urls)) {
            return false;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for (String url : urls) {
            if (matcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }
}
