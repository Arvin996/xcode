package cn.xk.xcode.core.lazy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * @Author xuk
 * @Date 2025/3/7 14:40
 * @Version 1.0.0
 * @Description LazyTaskProcessorParamsBuilder
 **/
public class LazyTaskProcessorParamsBuilder<T> {

    /**
     * 消费线程池实例【必填】
     */
    private ExecutorService executorService;
    /**
     * 阻塞队列实现类【必填】
     */
    private BlockingQueue<T> queue;
    /**
     * batch 触发执行的数量阈值【必填】
     */
    private Integer numThreshold;
    /**
     * batch 触发执行的时间阈值，单位毫秒【必填】
     */
    private Long timeThreshold;

    public static <T> LazyTaskProcessorParamsBuilder<T> create() {
        return new LazyTaskProcessorParamsBuilder<>();
    }

    public LazyTaskProcessorParamsBuilder<T> executorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public LazyTaskProcessorParamsBuilder<T> queue(BlockingQueue<T> queue) {
        this.queue = queue;
        return this;
    }

    public LazyTaskProcessorParamsBuilder<T> numThreshold(Integer numThreshold) {
        this.numThreshold = numThreshold;
        return this;
    }

    public LazyTaskProcessorParamsBuilder<T> timeThreshold(Long timeThreshold) {
        this.timeThreshold = timeThreshold;
        return this;
    }

    public LazyTaskProcessorParams<T> build() {
        return new LazyTaskProcessorParams<>(executorService, queue, numThreshold, timeThreshold);
    }
}
