package cn.xk.xcode.core.support;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.core.aop.aspect.Aspect;
import cn.xk.xcode.core.context.TraceContextHolder;
import cn.xk.xcode.core.entity.TraceRecord;
import cn.xk.xcode.exception.core.ServiceException;
import com.alibaba.fastjson2.JSON;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.message.MessageTopicType.TOPIC_TRACE_LOG;

/**
 * @Author xuk
 * @Date 2024/12/18 9:22
 * @Version 1.0.0
 * @Description AbstractThirdRequestAspect
 **/
public abstract class AbstractThirdRequestAspect implements Aspect {

    @Value("${spring.application.name}")
    private String serviceName;

    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;
    private TraceRecord traceRecord;
    private Long startTime;
    @Setter
    private String thirdRequestUrl;

    public AbstractThirdRequestAspect(RocketMQEnhanceTemplate rocketMQEnhanceTemplate) {
        this.rocketMQEnhanceTemplate = rocketMQEnhanceTemplate;
    }

    private void saveTraceRecord() {
        rocketMQEnhanceTemplate.sendAsync(TOPIC_TRACE_LOG.getType(), this.traceRecord);
    }

    @Override
    public boolean before(Object var1, Method var2, Object[] var3) {
        this.traceRecord = new TraceRecord();
        startTime = DateUtil.current();
        traceRecord.setTraceSort(TraceContextHolder.getTraceSort() + 1);
        traceRecord.setTraceId(TraceContextHolder.getTraceId());
        traceRecord.setRequestTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        Map<String, Object> map = new HashMap<>();
        Parameter[] parameters = var2.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            map.put(parameters[i].getName(), var3[i]);
        }
        traceRecord.setRequestParam(JSON.toJSONString(map));
        traceRecord.setCallServiceName(serviceName);
        traceRecord.setBeCalledServiceName("三方服务:" + thirdRequestUrl);
        traceRecord.setCallMethod(var2.getName());
        return true;
    }

    public abstract boolean isSuccess(Object var1);

    @Override
    public boolean after(Object var1, Method var2, Object[] var3, Object var4) {
        traceRecord.setResponseResult(var4.toString());
        traceRecord.setRequestTimeCost(DateUtil.current() - startTime);
        traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        traceRecord.setRequestResultCode(isSuccess(var4) ? 0 : 1);
        traceRecord.setBizKey(traceRecord.getTraceId());
        traceRecord.setMessageSource(serviceName);
        traceRecord.setSendTime(LocalDateTimeUtil.now());
        saveTraceRecord();
        return true;
    }

    @Override
    public boolean afterException(Object var1, Method var2, Object[] var3, Throwable var4) {
        traceRecord.setRequestTimeCost(DateUtil.current() - startTime);
        traceRecord.setResponseResult(var4.getMessage());
        traceRecord.setResponseTime(LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
        if (var4 instanceof ServiceException){
            traceRecord.setRequestResultCode(1);
        }else {
            traceRecord.setRequestResultCode(2);
        }
        traceRecord.setBizKey(traceRecord.getTraceId());
        traceRecord.setMessageSource(serviceName);
        traceRecord.setSendTime(LocalDateTimeUtil.now());
        saveTraceRecord();
        return true;
    }
}
