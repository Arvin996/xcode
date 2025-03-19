package cn.xk.xcode.core.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Author xuk
 * @Date 2025/3/7 13:39
 * @Version 1.0.0
 * @Description ThreadPoolProduceDecider
 **/
public class ThreadPoolProduceDecider {

    private static final String DEFAULT_CODE =  "default";

    public ThreadPoolProduceDecider(List<AbstractThreadPoolFactory> threadPoolProducerList) {
        threadPoolProducerList.forEach(factory -> {
            threadPoolProducerMap.put(factory.threadPoolType(), factory);
        });
    }

    private final Map<ThreadPoolTypeEnums, AbstractThreadPoolFactory> threadPoolProducerMap = new HashMap<>();

    public ExecutorService decide(ThreadPoolTypeEnums threadPoolTypeEnums, boolean coreThreadRecycle, String threadUniqueCode) {
        return threadPoolProducerMap.get(threadPoolTypeEnums).createThreadPool(coreThreadRecycle, threadUniqueCode);
    }

    public ExecutorService decide(ThreadPoolTypeEnums threadPoolTypeEnums, boolean coreThreadRecycle) {
        return decide(threadPoolTypeEnums, coreThreadRecycle, DEFAULT_CODE);
    }

    public ExecutorService decide(ThreadPoolTypeEnums threadPoolTypeEnums, String threadUniqueCode) {
        return decide(threadPoolTypeEnums, false, threadUniqueCode);
    }
}
