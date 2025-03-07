package cn.xk.xcode.core.lazy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * @Author xuk
 * @Date 2025/3/7 14:34
 * @Version 1.0.0
 * @Description LazyTaskProcessorParams
 **/
@Data
@AllArgsConstructor
public class LazyTaskProcessorParams<T> {

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
}
