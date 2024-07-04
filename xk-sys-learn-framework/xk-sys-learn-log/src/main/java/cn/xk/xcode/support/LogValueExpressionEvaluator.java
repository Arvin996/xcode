package cn.xk.xcode.support;

import lombok.NoArgsConstructor;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/7/4 10:59
 * @Version 1.0
 * @Description LogValueExpressionEvaluator
 */
@NoArgsConstructor
public class LogValueExpressionEvaluator extends CachedExpressionEvaluator {
    private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    public EvaluationContext createEvaluationContext(Class<?> targetClass, Method method, Object[] args, BeanFactory beanFactory) {
        Method targetMethod = this.getTargetMethod(targetClass, method);
        MethodBasedEvaluationContext evaluationContext =
                new LogValueEvaluationContext(null, targetMethod, args, getParameterNameDiscoverer());
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    public Object parseExpression(AnnotatedElementKey elementKey, String expression, EvaluationContext evalContext) {
        return getExpression(this.expressionCache, elementKey, expression).getValue(evalContext, Object.class);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        return this.targetMethodCache.computeIfAbsent(methodKey, (k) -> AopUtils.getMostSpecificMethod(method, targetClass));
    }

}
