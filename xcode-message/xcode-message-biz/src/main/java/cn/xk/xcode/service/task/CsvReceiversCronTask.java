//package cn.xk.xcode.service.task;
//
//import cn.xk.xcode.core.ThreadPoolExecutorHolder;
//import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
//import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
//import cn.xk.xcode.handler.corn.CsvReceiversCronTaskHandler;
//import com.dtp.core.thread.DtpExecutor;
//import com.xxl.job.core.context.XxlJobHelper;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
//import static cn.xk.xcode.config.GlobalMessageConstants.XXL_THREAD_POOL_NAME;
//
/// **
// * @Author xuk
// * @Date 2025/3/10 16:55
// * @Version 1.0.0
// * @Description CsvReceiversCronTask
// **/
//@Component
//public class CsvReceiversCronTask {
//
//    @Resource
//    private CsvReceiversCronTaskHandler csvReceiversCronTaskHandler;
//
//    @Resource
//    private ThreadPoolExecutorHolder threadPoolExecutorHolder;
//
//    private final DtpExecutor dtpExecutor = (DtpExecutor) threadPoolExecutorHolder.routeThreadPool(XXL_THREAD_POOL_NAME);
//
//    public void executeCsvReceiversCronTask() {
//        Integer taskId = Integer.valueOf(Objects.requireNonNull(XxlJobHelper.getJobParam()));
//        dtpExecutor.execute(() -> csvReceiversCronTaskHandler.handle(taskId));
//    }
//}
