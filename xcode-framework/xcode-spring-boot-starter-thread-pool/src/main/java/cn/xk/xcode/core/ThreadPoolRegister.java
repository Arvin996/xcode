package cn.xk.xcode.core;


import lombok.RequiredArgsConstructor;
import org.dromara.dynamictp.core.DtpRegistry;
import org.dromara.dynamictp.core.executor.DtpExecutor;
import org.dromara.dynamictp.core.support.ExecutorWrapper;

/**
 * @Author xuk
 * @Date 2025/3/7 10:47
 * @Version 1.0.0
 * @Description ThreadPoolRegister
 **/
@RequiredArgsConstructor
public class ThreadPoolRegister {

    private final ThreadPoolExecutorAllShutDownProcessor threadPoolExecutorAllShutDownProcessor;
    private static final String SOURCE_NAME = "xcode-pool";

    public void register(DtpExecutor executor) {
        threadPoolExecutorAllShutDownProcessor.registerThreadPool(executor);
        DtpRegistry.registerExecutor(new ExecutorWrapper(executor), SOURCE_NAME);
    }
}
