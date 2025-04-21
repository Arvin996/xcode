package cn.xk.xcode.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2025/4/21 8:19
 * @Version 1.0.0
 * @Description ThreadInfo
 **/
@Data
@NoArgsConstructor
public class ThreadPoolInfo {

    private String threadPoolName;
    private int corePoolSize;
    private int maxPoolSize;
    private int activeCount;
    private long completedTaskCount;
    private long taskCount;
    private int largestPoolSize;
    private int poolSize;
    private int queueSize;
    private String rejectedExecutionHandlerName;

}
