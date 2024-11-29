package cn.xk.xcode.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.context.EnvTagContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/11/29 9:23
 * @Version 1.0.0
 * @Description EnvIsolationFilter
 **/
public class EnvIsolationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tag = request.getHeader("tag");
        if (StrUtil.isEmpty(tag)) {
            filterChain.doFilter(request, response);
            return;
        }
        EnvTagContextHolder.setTag(tag);
        try {
            filterChain.doFilter(request, response);
        } finally {
            EnvTagContextHolder.removeTag();
        }
    }
}
