package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xuk
 * @Date 2024/7/25 15:09
 * @Version 1.0
 * @Description DefaultRateLimiterKeyResolver 默认（全局级别）限流 Key 解析器，服务名+请求uri+使用方法名 ，组装成一个 Key
 */
@RequiredArgsConstructor
public class DefaultRateLimiterKeyResolver implements RateLimiterKeyResolver
{
    private final String serviceName;

    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = getMethod(joinPoint).getName();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        return SaSecureUtil.md5(serviceName + requestUrl + methodName + StrUtil.join(",", joinPoint.getArgs()));
    }
}
