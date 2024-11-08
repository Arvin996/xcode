package cn.xk.xcode.utils;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * @Author xuk
 * @Date 2024/11/5 9:38
 * @Version 1.0.0
 * @Description TraceUtil
 **/
public class TraceUtil {

    private TraceUtil() {}

    public static String getTraceUd(){
        return TraceContext.traceId();
    }
}
