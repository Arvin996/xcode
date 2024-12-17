package cn.xk.xcode.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.TenantProperties;
import cn.xk.xcode.core.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/12/17 15:44
 * @Version 1.0.0
 * @Description TenantFilter
 **/
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final TenantProperties tenantProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (tenantProperties.getIgnoreUrls().contains(requestURI)){
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(TenantContext.TENANT_NAME);
        if (StrUtil.isNotEmpty(header)){
            TenantContext.setTenantId(Long.parseLong(header));
        }
        try {
            filterChain.doFilter(request, response);
        }finally {
            TenantContext.clear();
        }
    }
}
