package cn.xk.xcode.aop;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.annotation.BizLogRecord;
import cn.xk.xcode.context.LogValueContext;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.entity.BizAccessLog;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.support.LogValueParser;
import com.xk.xcode.core.entity.Area;
import com.xk.xcode.core.utils.AreaUtils;
import com.xk.xcode.core.utils.IpUtils;
import io.lettuce.core.dynamic.support.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

import static cn.xk.xcode.message.MessageTopicType.TOPIC_BIZ_LOG;

/**
 * @Author xuk
 * @Date 2024/7/4 13:49
 * @Version 1.0
 * @Description BizLogAnnotationInterceptor
 */
@RequiredArgsConstructor
@Slf4j
public class BizLogAnnotationInterceptor implements MethodInterceptor {

    private final LogValueParser logValueParser;

    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        return this.execute(invocation, invocation.getThis(), method, invocation.getArguments());
    }

    @SuppressWarnings("all")
    private Object execute(MethodInvocation invoker, Object target, Method method, Object[] args) throws Throwable {
        if (AopUtils.isAopProxy(target)) {
            return invoker.proceed();
        }
        StopWatch stopWatch = new StopWatch("log-record-performance");
        stopWatch.start("before-execute");
        BizAccessLog bizAccessLog = new BizAccessLog();
        bizAccessLog.setStartTime(LocalDateTime.now());
        bizAccessLog.setUserId(StpUtil.getLoginIdAsString());
        bizAccessLog.setLoginType((String) StpUtil.getExtra("loginType"));
        Class<?> targetClass = this.getTargetClass(target);
        BizLogRecord bizLogRecord = method.getAnnotation(BizLogRecord.class);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        bizAccessLog.setUrl(request.getRequestURI());
        bizAccessLog.setContentType(request.getContentType());
        bizAccessLog.setIp(ServletUtil.getClientIP(request));
        Area area = IpUtils.getArea(ServletUtil.getClientIP(request));
        String address = area.getName();
        while (area.getParentArea() != null) {
            area = area.getParentArea();
            address = area.getParentArea().getName() + "/" + address;
        }
        bizAccessLog.setIpAddress(address);
        bizAccessLog.setClassName(method.getDeclaringClass().getName());
        bizAccessLog.setMethod(method.getName());
        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            Object arg = args[i];
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                map.put(parameterNames[i], "HttpServlet");
            }else {
                 map.put(parameterNames[i], arg);
            }
        }
        bizAccessLog.setMethodParams(JSONUtil.toJsonStr(map));
        bizAccessLog.setTraceId(request.getHeader("traceId"));
        bizAccessLog.setDesc(bizLogRecord.desc());
        bizAccessLog.setBizType(bizLogRecord.bizType());
        // spel处理
        String bizIdExpress = bizLogRecord.bizId();
        map = logValueParser.resolveElValues(Collections.singleton(bizIdExpress), targetClass, method, args);
        bizAccessLog.setBizId((String) map.get(bizIdExpress));
        Object proceed;
        try{
            proceed = invoker.proceed();
        }catch(Exception e){
            log.error(e.getStackTrace().toString());
            throw new ServiceException(e.getMessage());
        }
        // 清楚threadlocal
        LogValueContext.clearVariable();
        stopWatch.stop();
        bizAccessLog.setEndTime(LocalDateTime.now());
        bizAccessLog.setDuration(stopWatch.getTotalTimeMillis());
        bizAccessLog.setResult(Objects.isNull(proceed) ? null : proceed instanceof String ? proceed.toString() : JSONUtil.toJsonStr(proceed));
        rocketMQEnhanceTemplate.sendAsync(TOPIC_BIZ_LOG.getType(), bizAccessLog, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("{}异步消息{}发送成功", TOPIC_BIZ_LOG.getType(), JSONUtil.toJsonStr(bizAccessLog));
            }
            @Override
            public void onException(Throwable throwable) {
                log.error("{}异步消息{}发送失败:{}", TOPIC_BIZ_LOG.getType(), JSONUtil.toJsonStr(bizAccessLog), throwable.getMessage());
            }
        });
        return proceed;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }
}
