package cn.xk.xcode.core.resolver.impl;

import cn.xk.xcode.annotation.Idempotent;
import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import cn.xk.xcode.utils.spring.SpringExpressionUtil;
import org.aspectj.lang.JoinPoint;

/**
 * @Author xuk
 * @Date 2024/7/25 15:03
 * @Version 1.0
 * @Description ExpressionIdempotentKeyResolver
 */
public class ExpressionIdempotentKeyResolver implements IdempotentKeyResolver
{
    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        return (String) SpringExpressionUtil.parseExpression(joinPoint, idempotent.IdeKey());
    }
}
