package cn.xk.xcode.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2025/3/7 10:47
 * @Version 1.0.0
 * @Description ThreadPoolExecutorAllShutDownProcessor
 **/
@Slf4j
public class ThreadPoolExecutorAllShutDownProcessor implements DisposableBean {

    private final static Long AWAIT_TERMINATION = 20L;
    private final static TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final static List<ExecutorService> THREAD_POOLS = new CopyOnWriteArrayList<>();

    public void registerThreadPool(ExecutorService executorService) {
        THREAD_POOLS.add(executorService);
    }

    @Override
    public void destroy() {
        log.info("容器关闭前处理线程池优雅关闭开始, 当前要处理的线程池数量为: {} >>>>>>>>>>>>>>>>", THREAD_POOLS.size());
        if (THREAD_POOLS.isEmpty()) {
            return;
        }
        for (ExecutorService pool : THREAD_POOLS) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(AWAIT_TERMINATION, TIME_UNIT)) {
                    log.warn("线程池{}到达指定时间未释放， 将强制关闭", pool);
                    pool.shutdownNow();
                }
            } catch (InterruptedException ex) {
                log.warn("Timed out while waiting for executor [{}] to terminate", pool);
                Thread.currentThread().interrupt();
            }
        }
    }
}
