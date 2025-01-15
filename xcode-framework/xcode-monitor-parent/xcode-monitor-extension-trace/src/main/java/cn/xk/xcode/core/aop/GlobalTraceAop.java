package cn.xk.xcode.core.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.core.annotation.ThirdApi;
import cn.xk.xcode.core.context.TraceContextHolder;
import cn.xk.xcode.entity.TraceRecord;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.pojo.CommonResult;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.message.MessageTopicType.TOPIC_TRACE_LOG;

/**
 * @Author xuk
 * @Date 2024/12/18 11:33
 * @Version 1.0.0
 * @Description GlobalTraceAop
 **/
@Aspect
@Slf4j
public class GlobalTraceAop {

    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    public GlobalTraceAop(RocketMQEnhanceTemplate rocketMQEnhanceTemplate) {
        this.rocketMQEnhanceTemplate = rocketMQEnhanceTemplate;
    }

    @Value("${spring.application.name}")
    private String serviceName;

    private final static ErrorCode THIRD_API_NOT_PUT_TRACE_ID = new IntErrorCode(1400_0_600, "ThirdApi标记的接口未放方法逻辑中设置traceId");

    @SuppressWarnings("all")
    @Around("execution(public * cn.xk.xcode.controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long current = DateUtil.current();
        String callService = TraceContextHolder.getCallService();
        Integer traceSort = TraceContextHolder.getTraceSort();
        TraceRecord traceRecord = new TraceRecord();
        traceRecord.setTraceSort(traceSort);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        traceRecord.setRequestTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            // 文件上传不需要记录参数
            if (arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            if (ClassUtil.isBasicType(parameters[i].getType()) || String.class.isAssignableFrom(parameters[i].getType())){
                map.put(parameters[i].getName(), arg);
                continue;
            }
            map.put(parameters[i].getName(), JSON.toJSONString(arg));
        }
        traceRecord.setRequestUrl(TraceContextHolder.getRequestUrl());
        traceRecord.setRequestParam(JSON.toJSONString(map));
        traceRecord.setCallServiceName(callService);
        traceRecord.setBeCalledServiceName(serviceName);
        traceRecord.setCallMethod(method.getName());
        Object result;
        try {
            result = joinPoint.proceed();
            traceRecord.setResponseResult(JSON.toJSONString(result));
            traceRecord.setRequestTimeCost(DateUtil.current() - current);
            traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
            if (result instanceof CommonResult) {
                CommonResult<?> commonResult = (CommonResult<?>) result;
                if (commonResult.isSuccess()) {
                    traceRecord.setRequestResultCode(0);
                } else {
                    traceRecord.setRequestResultCode(1);
                }
            }
            if (method.isAnnotationPresent(ThirdApi.class)) {
                ThirdApi thirdApi = method.getAnnotation(ThirdApi.class);
                if (thirdApi.autoGenTraceId()) {
                    TraceContextHolder.setTraceId(IdUtil.fastSimpleUUID());
                } else {
                    if (StrUtil.isBlank(TraceContextHolder.getTraceId())) {
                        // 报错
                        ExceptionUtil.castServerException(THIRD_API_NOT_PUT_TRACE_ID);
                    }
                }
            }
            if (StrUtil.isBlank(TraceContextHolder.getTraceId())) {
                log.warn("traceId为空, 将不记录链路日志，请检查该接口是否是被三方调用，是否有@ThirdApi注解");
            } else {
                traceRecord.setTraceId(TraceContextHolder.getTraceId());
                traceRecord.setBizKey(traceRecord.getTraceId());
                traceRecord.setMessageSource(serviceName);
                traceRecord.setSendTime(LocalDateTime.now());
                rocketMQEnhanceTemplate.sendAsync(TOPIC_TRACE_LOG.getType(), traceRecord);
            }
            return result;
        } catch (Throwable e) {
            traceRecord.setResponseResult(e.getMessage());
            traceRecord.setRequestTimeCost(DateUtil.current() - current);
            traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
            if (e instanceof ServiceException) {
                traceRecord.setRequestResultCode(1);
            }else {
                traceRecord.setRequestResultCode(2);
            }
            if (method.isAnnotationPresent(ThirdApi.class)) {
                ThirdApi thirdApi = method.getAnnotation(ThirdApi.class);
                if (thirdApi.autoGenTraceId()) {
                    TraceContextHolder.setTraceId(IdUtil.fastSimpleUUID());
                } else {
                    if (StrUtil.isBlank(TraceContextHolder.getTraceId())) {
                        // 报错
                        log.error("三方接口出现异常， 可能在异常之前traceId未被设置进去，请检查");
                        ExceptionUtil.castServerException(THIRD_API_NOT_PUT_TRACE_ID);
                    }
                }
            }
            traceRecord.setExceptionMessage(e.getMessage());
            if (StrUtil.isBlank(TraceContextHolder.getTraceId())) {
                log.warn("traceId为空, 将不记录链路日志，请检查该接口是否是被三方调用，是否有@ThirdApi注解");
            } else {
                traceRecord.setTraceId(TraceContextHolder.getTraceId());
                traceRecord.setBizKey(traceRecord.getTraceId());
                traceRecord.setMessageSource(serviceName);
                traceRecord.setSendTime(LocalDateTime.now());
                rocketMQEnhanceTemplate.sendAsync(TOPIC_TRACE_LOG.getType(), traceRecord);
            }
            throw new Throwable(e);
        } finally {
            TraceContextHolder.clear();
        }
    }
}
