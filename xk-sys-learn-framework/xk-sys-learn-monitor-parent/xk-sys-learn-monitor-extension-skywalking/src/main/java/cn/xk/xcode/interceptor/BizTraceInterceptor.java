package cn.xk.xcode.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import cn.xk.xcode.annotation.BizTrace;
import cn.xk.xcode.utils.spring.SpringExpressionUtil;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.annotation.BizTrace.ID_TAG;
import static cn.xk.xcode.annotation.BizTrace.TYPE_TAG;


/**
 * @Author xuk
 * @Date 2024/11/5 10:09
 * @Version 1.0.0
 * @Description BizTraceInterceptor
 **/
@RequiredArgsConstructor
@Slf4j
public class BizTraceInterceptor implements MethodInterceptor {

    private final Tracer tracer;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        Method method = invocation.getMethod();
        BizTrace bizTrace = method.getAnnotation(BizTrace.class);
        Span span;
        if (ObjectUtil.isNotNull(bizTrace)) {
            // 设置
            return invocation.proceed();
        }
        span = tracer.buildSpan(getOperationName(bizTrace, invocation)).withTag(Tags.COMPONENT.getKey(), "biz")
                .startManual();
        try {
            result = invocation.proceed();
            return result;
        } catch (Throwable throwable) {
            if (span != null) {
                Tags.ERROR.set(span, Boolean.TRUE);
                span.log(errorLogs(throwable));
            }
            throw new Throwable(throwable);
        } finally {
            if (span != null) {
                setBizTag(span, bizTrace, invocation);
                span.finish();
            }
        }
    }

    private void setBizTag(Span span, BizTrace bizTrace, MethodInvocation invocation) {
        String bizType = bizTrace.bizType();
        String bizId = bizTrace.bizId();
        if (StrUtil.isNotBlank(bizType)) {
            span.setTag(TYPE_TAG, bizType);
        }
        if (StrUtil.isNotBlank(bizId)) {
            try {
                Map<String, Object> map = SpringExpressionUtil.parseExpressions(invocation.getMethod(), invocation.getArguments(), bizId);
                span.setTag(ID_TAG, MapUtil.getStr(map, bizId));
            }catch (Exception e){
                log.error("[setBizTag][解析 bizType 与 bizId 发生异常],错误信息:{}", e.getMessage());
            }
        }
    }

    private static Map<String, Object> errorLogs(Throwable throwable) {
        Map<String, Object> errorLogs = new HashMap<String, Object>(10);
        errorLogs.put("event", Tags.ERROR.getKey());
        errorLogs.put("error.object", throwable);
        errorLogs.put("error.kind", throwable.getClass().getName());
        String message = throwable.getCause() != null ? throwable.getCause().getMessage() : throwable.getMessage();
        if (message != null) {
            errorLogs.put("message", message);
        }
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        errorLogs.put("stack", sw.toString());
        return errorLogs;
    }

    private String getOperationName(BizTrace bizTrace, MethodInvocation methodInvocation) {
        String operationName = bizTrace.operationName();
        if (StrUtil.isBlank(operationName)) {
            Method method = methodInvocation.getMethod();
            Class<?> clazz = methodInvocation.getThis() != null ? methodInvocation.getThis().getClass() : method.getDeclaringClass();
            operationName = clazz.getSimpleName() + "/" + method.getName();
        }
        return operationName;
    }

}
