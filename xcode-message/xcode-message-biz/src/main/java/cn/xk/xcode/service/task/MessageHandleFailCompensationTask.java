//package cn.xk.xcode.service.task;
//
//import cn.xk.xcode.annotation.AutoRegisterXxlJob;
//import cn.xk.xcode.core.ThreadPoolExecutorHolder;
//import cn.xk.xcode.handler.MessageHandlerHolder;
//import com.dtp.core.thread.DtpExecutor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//import static cn.xk.xcode.config.GlobalMessageConstants.XXL_THREAD_POOL_NAME;
//
/// **
// * @Author xuk
// * @Date 2025/4/18 11:49
// * @Version 1.0.0
// * @Description MessageHandleFailCompensationTask
// **/
//@Component
//public class MessageHandleFailCompensationTask {
//
//    @Resource
//    private MessageHandlerHolder messageHandlerHolder;
//
//    @Resource
//    private ThreadPoolExecutorHolder threadPoolExecutorHolder;
//
//    private final DtpExecutor dtpExecutor = (DtpExecutor) threadPoolExecutorHolder.routeThreadPool(XXL_THREAD_POOL_NAME);
//
//    @AutoRegisterXxlJob(executorHandler = "sendMessageHandleFailCompensationTask",
//            cron = "0 0/1 * * * ?",
//            author = "arvin",
//            jobDesc = "发送消息失败补偿",
//            triggerStatus = 1)
//    public void sendMessageHandleFailCompensationTask() {
//        // 1. 获取失败的消息发送
//        // 2. 使用线程池+countdownlatch执行
//        // 3. 更新状态
//    }
//
//
//    @AutoRegisterXxlJob(executorHandler = "withdrawMessageHandleFailCompensationTask",
//            cron = "0 0/1 * * * ?",
//            author = "arvin",
//            jobDesc = "撤销消息失败补偿",
//            triggerStatus = 1)
//    public void withdrawMessageHandleFailCompensationTask() {
//        // 1. 获取失败的消息撤销
//        // 2. 使用线程池+countdownlatch执行
//        // 3. 更新状态
//    }
//}
