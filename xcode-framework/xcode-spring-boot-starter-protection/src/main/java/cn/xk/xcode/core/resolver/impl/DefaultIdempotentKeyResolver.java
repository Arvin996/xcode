package cn.xk.xcode.core.resolver.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.Idempotent;
import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author xuk
 * @Date 2024/7/23 17:00
 * @Version 1.0
 * @Description DefaultIdempotentKeyResolver  默认（全局级别）幂等 Key 解析器，服务名+使用请求url+方法名+参数名，组装成一个 Key
 */
@RequiredArgsConstructor
public class DefaultIdempotentKeyResolver implements IdempotentKeyResolver {

    private final String serviceName;

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = getMethod(joinPoint).getName();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String requestUrl = ObjectUtil.isNull(httpServletRequest) ? "" : httpServletRequest.getRequestURI();
        return SaSecureUtil.md5(serviceName + requestUrl + methodName + StrUtil.join(",", joinPoint.getArgs()));
    }

}
