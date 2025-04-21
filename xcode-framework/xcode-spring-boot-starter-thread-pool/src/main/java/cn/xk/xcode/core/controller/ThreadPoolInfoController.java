package cn.xk.xcode.core.controller;

import cn.xk.xcode.core.entity.RefreshThreadPool;
import cn.xk.xcode.core.entity.ThreadPoolInfo;
import cn.xk.xcode.pojo.CommonResult;
import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/4/21 8:16
 * @Version 1.0.0
 * @Description TheadInfoController
 **/
@RequestMapping("/threadPool/info")
@SuppressWarnings("all")
public class ThreadPoolInfoController {

    private final Map<String, DtpExecutor> threadPools = new HashMap<>();

    @PostConstruct
    public void init() {
        DtpRegistry.listAllDtpNames().forEach(name -> {
            threadPools.put(name, DtpRegistry.getExecutor(name));
        });
    }

    @GetMapping("/all")
    public CommonResult<List<ThreadPoolInfo>> allThreadPoolInfo() {
        List<ThreadPoolInfo> threadPoolInfos = new ArrayList<>();
        threadPools.forEach((k, v) -> {
            ThreadPoolInfo threadPoolInfo = new ThreadPoolInfo();
            threadPoolInfo.setThreadPoolName(k);
            threadPoolInfo.setCorePoolSize(v.getCorePoolSize());
            threadPoolInfo.setMaxPoolSize(v.getMaximumPoolSize());
            threadPoolInfo.setActiveCount(v.getActiveCount());
            threadPoolInfo.setCompletedTaskCount(v.getCompletedTaskCount());
            threadPoolInfo.setTaskCount(v.getTaskCount());
            threadPoolInfo.setLargestPoolSize(v.getLargestPoolSize());
            threadPoolInfo.setPoolSize(v.getPoolSize());
            threadPoolInfo.setQueueSize(v.getQueue().size());
            threadPoolInfo.setRejectedExecutionHandlerName(v.getRejectedExecutionHandler().getClass().getSimpleName());
            threadPoolInfos.add(threadPoolInfo);
        });
        return CommonResult.success(threadPoolInfos);
    }

    @GetMapping("/single/{name}")
    public CommonResult<ThreadPoolInfo> singleThreadPoolInfo(@PathVariable("name") String name) {
        DtpExecutor dtpExecutor = threadPools.get(name);
        if (dtpExecutor == null) {
            return CommonResult.error("", "线程池" + name + "不存在");
        }
        ThreadPoolInfo threadPoolInfo = new ThreadPoolInfo();
        threadPoolInfo.setThreadPoolName(name);
        threadPoolInfo.setCorePoolSize(dtpExecutor.getCorePoolSize());
        threadPoolInfo.setMaxPoolSize(dtpExecutor.getMaximumPoolSize());
        threadPoolInfo.setActiveCount(dtpExecutor.getActiveCount());
        threadPoolInfo.setCompletedTaskCount(dtpExecutor.getCompletedTaskCount());
        threadPoolInfo.setTaskCount(dtpExecutor.getTaskCount());
        threadPoolInfo.setLargestPoolSize(dtpExecutor.getLargestPoolSize());
        threadPoolInfo.setPoolSize(dtpExecutor.getPoolSize());
        threadPoolInfo.setQueueSize(dtpExecutor.getQueue().size());
        threadPoolInfo.setRejectedExecutionHandlerName(dtpExecutor.getRejectedExecutionHandler().getClass().getSimpleName());
        return CommonResult.success(threadPoolInfo);
    }

    @PostMapping("/refresh")
    public CommonResult<Boolean> refreshThreadPoolInfo(@RequestBody RefreshThreadPool refreshThreadPool) {
        DtpExecutor dtpExecutor = threadPools.get(refreshThreadPool.getThreadPoolName());
        if (dtpExecutor == null) {
            return CommonResult.success(false);
        }
        DtpRegistry.refresh(dtpExecutor, refreshThreadPool);
        return CommonResult.success(true);
    }
}
