package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xukai
 * @version 1.0
 * @date 2023/8/15 15:56
 * @Description 线程池配置类
 */
@Data
@Configuration
@ConfigurationProperties("xcode.message.thread")
public class ThreadPoolExecutorProperties {

    // 系统核心数
    private int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private int MAX_POOL_SIZE = 2 * CORE_POOL_SIZE;
    private int QUEUE_CAPACITY = 100;
    private long KEEP_ALIVE_TIME = 1L;

}
