package cn.xk.xcode.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Author xuk
 * @Date 2024/12/18 10:23
 * @Version 1.0.0
 * @Description TraceContextHolder
 **/
public class TraceContextHolder {

    public static final String TRACE_ID_KEY = "traceId";
    public static final String TRACE_SORT_KEY = "traceSort";
    public static final String CALL_SERVICE_KEY = "callService";

    private static final ThreadLocal<String> TRACE_ID = new TransmittableThreadLocal<>();
    private static final ThreadLocal<Integer> TRACE_SORT = new TransmittableThreadLocal<>();
    private static final ThreadLocal<String> CALL_SERVICE = new TransmittableThreadLocal<>();
    private static final ThreadLocal<String> REQUEST_URL = new TransmittableThreadLocal<>();

    public static void setRequestUrl(String requestUrl) {
        REQUEST_URL.set(requestUrl);
    }

    public static String getRequestUrl() {
        return REQUEST_URL.get();
    }

    public static void setCallService(String callService) {
        CALL_SERVICE.set(callService);
    }

    public static String getCallService() {
        return CALL_SERVICE.get();
    }

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static void setTraceSort(Integer traceSort) {
        TRACE_SORT.set(traceSort);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static Integer getTraceSort() {
        return TRACE_SORT.get();
    }

    public static void clear() {
        TRACE_ID.remove();
        TRACE_SORT.remove();
        CALL_SERVICE.remove();
        REQUEST_URL.remove();
    }

}
