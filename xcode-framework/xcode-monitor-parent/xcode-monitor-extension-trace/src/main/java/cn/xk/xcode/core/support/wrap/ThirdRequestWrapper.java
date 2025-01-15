package cn.xk.xcode.core.support.wrap;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.reflect.MethodHandleUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.core.context.TraceContextHolder;
import cn.xk.xcode.entity.TraceRecord;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static cn.xk.xcode.message.MessageTopicType.TOPIC_TRACE_LOG;

/**
 * @Author xuk
 * @Date 2025/1/15 13:56
 * @Version 1.0.0
 * @Description ThirdRequestWrapper
 **/
public class ThirdRequestWrapper {

    @Value("${spring.application.name}")
    private String serviceName;

    // 请求路由
    private final String url;
    // http客户端
    private final Object request;
    // 请求方法名称
    private final String methodName;
    // 请求参数
    private final Object[] args;
    // 解析是否发生异常
    private final Function<Object, Boolean> function;
    private final String requestStr;

    private long startTime;
    private TraceRecord traceRecord;
    private Method method;

    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate = SpringUtil.getBean(RocketMQEnhanceTemplate.class);

    ErrorCode METHOD_NOT_FIND = new IntErrorCode(1400_0_600, "类{}中不存在请求方法{}，请检查传参和方法名称是否对应");

    public ThirdRequestWrapper(String url, Object request, String methodName, Object[] args, Function<Object, Boolean> function, String requestStr) {
        this.url = url;
        this.request = request;
        this.methodName = methodName;
        this.args = args;
        this.function = function;
        this.requestStr = requestStr;
        resolveMethod();
    }


    public Object process() {
        beforeProcess();
        Object invoke;
        try {
            invoke = MethodHandleUtil.invoke(request, method, args);
        } catch (Exception e) {
            afterException(e);
            throw new RuntimeException(e);
        }
        afterProcess(invoke);
        return invoke;
    }

    public void resolveMethod() {
        Class<?>[] argsType = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            argsType[i] = args[i].getClass();
        }
        try {
            method = request.getClass().getMethod(methodName, argsType);
        } catch (NoSuchMethodException e) {
            ExceptionUtil.castServerException(METHOD_NOT_FIND, request.getClass().getSimpleName(), methodName);
        }
    }


    public void beforeProcess() {
        traceRecord = new TraceRecord();
        startTime = DateUtil.current();
        traceRecord.setTraceSort(TraceContextHolder.getTraceSort() + 1);
        traceRecord.setTraceId(TraceContextHolder.getTraceId());
        traceRecord.setRequestTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
//        Map<String, Object> map = new HashMap<>();
//        Parameter[] parameters = method.getParameters();
//        for (int i = 0; i < args.length; i++) {
//            map.put(parameters[i].getName(), args[i]);
//        }
        traceRecord.setRequestParam(requestStr);
        traceRecord.setCallServiceName(serviceName);
        traceRecord.setRequestUrl(this.url);
        traceRecord.setBeCalledServiceName("三方服务:" + this.url);
        traceRecord.setCallMethod(methodName);
    }

    public void afterProcess(Object result) {
        traceRecord.setResponseResult(result.toString());
        traceRecord.setRequestTimeCost(DateUtil.current() - startTime);
        traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        traceRecord.setRequestResultCode(function.apply(result) ? 0 : 1);
        traceRecord.setBizKey(traceRecord.getTraceId());
        traceRecord.setMessageSource(serviceName);
        traceRecord.setSendTime(LocalDateTimeUtil.now());
        rocketMQEnhanceTemplate.sendAsync(TOPIC_TRACE_LOG.getType(), this.traceRecord);
    }

    public void afterException(Throwable throwable) {
        traceRecord.setRequestTimeCost(DateUtil.current() - startTime);
        traceRecord.setResponseResult(throwable.getMessage());
        traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        if (throwable instanceof ServiceException) {
            traceRecord.setRequestResultCode(1);
        } else {
            traceRecord.setRequestResultCode(2);
        }
        traceRecord.setBizKey(traceRecord.getTraceId());
        traceRecord.setMessageSource(serviceName);
        traceRecord.setSendTime(LocalDateTimeUtil.now());
        rocketMQEnhanceTemplate.sendAsync(TOPIC_TRACE_LOG.getType(), this.traceRecord);
    }
}
