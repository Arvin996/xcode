package cn.xk.xcode.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/4 11:15
 * @Version 1.0
 * @Description LogValueParser
 */
@Component
public class LogValueParser implements BeanFactoryAware
{
    protected BeanFactory beanFactory;

    private final LogValueExpressionEvaluator logValueExpressionEvaluator = new LogValueExpressionEvaluator();

    @SuppressWarnings("all")
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Map<String, Object> resolveElValues(Collection<String> templates, Class<?> targetClass, Method method, Object[] args){
        Map<String, Object> values = new HashMap<>();
        EvaluationContext evaluationContext = logValueExpressionEvaluator.createEvaluationContext(targetClass, method, args, beanFactory);
        while (templates.iterator().hasNext()){
            String template = templates.iterator().next();
            Object value = logValueExpressionEvaluator.parseExpression(new AnnotatedElementKey(method, targetClass), template, evaluationContext);
            values.put(template, value);
        }
        return values;
    }
}
