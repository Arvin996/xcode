package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xuk
 * @Date 2024/7/25 16:26
 * @Version 1.0
 * @Description UserRateLimiterKeyResolver
 */
public class UserRateLimiterKeyResolver implements RateLimiterKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String userId = StpUtil.getLoginIdAsString();
        String loginType = (String) StpUtil.getExtra("loginType");
        String methodName = getMethod(joinPoint).getName();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        return SaSecureUtil.md5(requestUrl + methodName + userId + loginType + StrUtil.join(",", joinPoint.getArgs()));
    }
}
