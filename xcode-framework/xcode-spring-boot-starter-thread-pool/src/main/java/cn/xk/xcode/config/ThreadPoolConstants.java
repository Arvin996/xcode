package cn.xk.xcode.config;

/**
 * @Author xuk
 * @Date 2025/3/7 10:43
 * @Version 1.0.0
 * @Description ThreadPoolConstants
 **/
public interface ThreadPoolConstants {

    /**
     * 阻塞队列大小
     */
    Integer PENDING_QUEUE_SIZE = 100;
    /**
     * 触发执行的数量阈值
     */
    Integer PENDING_NUM_THRESHOLD = 100;
    /**
     * batch 触发执行的时间阈值，单位毫秒【必填】
     */
    Long PENDING_TIME_THRESHOLD = 1000L;

    /**
     * 单核心线程池
     */
    Integer SINGLE_CORE_POOL_SIZE = 1;
    Integer SINGLE_MAX_POOL_SIZE = 1;
    Integer SMALL_KEEP_LIVE_TIME = 10;
    /**
     * 双核心线程池
     */
    Integer COMMON_CORE_POOL_SIZE = 2;
    Integer COMMON_MAX_POOL_SIZE = 2;
    Integer COMMON_KEEP_LIVE_TIME = 60;
    Integer COMMON_QUEUE_SIZE = 128;
    /**
     * big queue size
     */
    Integer BIG_QUEUE_SIZE = 1024;
}
