package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xuk
 * @Date 2024/7/25 16:23
 * @Version 1.0
 * @Description ServerNodeRateLimiterKeyResolver 节点级别的限流 Key 解析器，请求uri + 方法参数 + IP，组装成一个 Key
 */
public class ServerNodeRateLimiterKeyResolver implements RateLimiterKeyResolver
{
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = joinPoint.getSignature().toString();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        String serverNode = String.format("%s@%d", SystemUtil.getHostInfo().getAddress(), SystemUtil.getCurrentPID());
        return SaSecureUtil.md5(methodName + requestUrl + serverNode + StrUtil.join(",", joinPoint.getArgs()));
    }
}
