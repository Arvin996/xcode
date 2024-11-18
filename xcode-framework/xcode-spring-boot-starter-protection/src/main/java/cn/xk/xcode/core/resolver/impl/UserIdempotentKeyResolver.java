package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.Idempotent;
import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xuk
 * @Date 2024/7/25 14:57
 * @Version 1.0
 * @Description UserIdempotentKeyResolver 用户级别的幂等 Key 解析器，使用方法名 + 请求uri + userId + loginType，组装成一个 Key
 */
public class UserIdempotentKeyResolver implements IdempotentKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = getMethod(joinPoint).getName();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        String userId = StpUtil.getLoginIdAsString();
        String loginType = (String) StpUtil.getExtra("loginType");
        return SaSecureUtil.md5(methodName + requestUrl + userId + loginType + StrUtil.join(",", joinPoint.getArgs()));
    }
}
