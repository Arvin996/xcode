package cn.xk.xcode.core.lazy;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
import com.google.common.base.Throwables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2025/3/7 14:21
 * @Version 1.0.0
 * @Description AbstractLazyTaskProcess
 * 延迟消息 生产者 消费者模式 使用阻塞队列实现
 * T 为消费实体
 **/
@Data
@Slf4j
public abstract class AbstractLazyTaskProcessor<T> {

    protected LazyTaskProcessorParams<T> params;
    /**
     * 批量装载任务
     */
    private List<T> tasks = new ArrayList<>();

    /**
     * 上次执行的时间
     */
    private Long lastHandleTime = System.currentTimeMillis();

    /**
     * 是否终止线程
     */
    private volatile Boolean stop = false;

    public AbstractLazyTaskProcessor(){}


    public static <T> LazyTaskProcessorParamsBuilder<T> builder() {
        return new LazyTaskProcessorParamsBuilder<>();
    }

    @PostConstruct
    public void initLazyTaskProcessor() {
        ThreadPoolProduceDecider threadPoolProduceDecider = SpringUtil.getBean(ThreadPoolProduceDecider.class);
        ExecutorService executorService = threadPoolProduceDecider.decide(ThreadPoolTypeEnums.SINGLE, true);
        executorService.execute(() -> {
            while (true) {
                try {
                    T obj = params.getQueue().poll(params.getTimeThreshold(), TimeUnit.MILLISECONDS);
                    if (Objects.nonNull(obj)) {
                        tasks.add(obj);
                    }
                    if (Boolean.TRUE.equals(stop) && tasks.isEmpty()) {
                        executorService.shutdown();
                        break;
                    }
                    if (!tasks.isEmpty() && isReadyProcess()) {
                        List<T> taskRef = tasks;
                        tasks = new ArrayList<>();
                        lastHandleTime = System.currentTimeMillis();
                        params.getExecutorService().execute(() -> this.process(taskRef));
                    }
                } catch (Exception e) {
                    log.error("AbstractLazyTaskProcessor#initLazyTaskProcessor failed:{}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void push(T t) {
        try {
            params.getQueue().put(t);
        } catch (InterruptedException e) {
            log.error("Pending#pending error:{}", Throwables.getStackTraceAsString(e));
            Thread.currentThread().interrupt();
        }
    }

    private void process(List<T> taskRef) {
        if (taskRef.isEmpty()) {
            return;
        }
        try {
            doProcess(taskRef);
        } catch (Exception e) {
            log.error("AbstractLazyTaskProcessor#process failed:{}", e.getMessage());
        }
    }

    public abstract void doProcess(List<T> taskRef);

    private boolean isReadyProcess() {
        return tasks.size() >= params.getNumThreshold() ||
                (System.currentTimeMillis() - lastHandleTime >= params.getTimeThreshold());
    }

}
