package cn.xk.xcode.support;

import cn.xk.xcode.context.LogValueContext;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/4 10:56
 * @Version 1.0
 * @Description LogValueEvaluationContext
 */
public class LogValueEvaluationContext extends MethodBasedEvaluationContext {

    public LogValueEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
        Map<String, Object> variableMap = LogValueContext.getVariableMap();
        if (variableMap != null){
            this.setVariables(variableMap);
        }
    }
}
