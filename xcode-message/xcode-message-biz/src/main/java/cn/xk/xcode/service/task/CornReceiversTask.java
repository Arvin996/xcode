package cn.xk.xcode.service.task;

import cn.xk.xcode.core.ThreadPoolExecutorHolder;
import cn.xk.xcode.handler.corn.CornReceiversTaskHandler;
import com.xxl.job.core.context.XxlJobHelper;
import org.dromara.dynamictp.core.executor.DtpExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.xk.xcode.config.GlobalMessageConstants.XXL_THREAD_POOL_NAME;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 11:13
 * @description CsvReceiversCronTask
 */
@Component
public class CornReceiversTask {


    private final CornReceiversTaskHandler cornReceiversTaskHandler;

    private final DtpExecutor dtpExecutor;

    public CornReceiversTask(CornReceiversTaskHandler cornReceiversTaskHandler, ThreadPoolExecutorHolder threadPoolExecutorHolder) {
        this.cornReceiversTaskHandler = cornReceiversTaskHandler;
        dtpExecutor = (DtpExecutor) threadPoolExecutorHolder.routeThreadPool(XXL_THREAD_POOL_NAME);
    }

    public void executeCsvReceiversCronTask() {
        Integer taskId = Integer.valueOf(Objects.requireNonNull(XxlJobHelper.getJobParam()));
        dtpExecutor.execute(() -> cornReceiversTaskHandler.handle(taskId));
    }
}
