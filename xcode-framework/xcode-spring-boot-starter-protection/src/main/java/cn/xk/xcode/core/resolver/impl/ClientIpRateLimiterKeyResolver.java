package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/7/25 16:17
 * @Version 1.0
 * @Description ClientIpRateLimiterKeyResolver 使用方法名 + 请求uri + IP，组装成一个 Key
 */
public class ClientIpRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = getMethod(joinPoint).getName();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        String clientIP = "";
        if (!Objects.isNull(httpServletRequest)){
           clientIP =  ServletUtil.getClientIP(httpServletRequest);
        }
        return SaSecureUtil.md5(methodName + requestUrl + StrUtil.join(",", joinPoint.getArgs()) + clientIP);
    }
}
