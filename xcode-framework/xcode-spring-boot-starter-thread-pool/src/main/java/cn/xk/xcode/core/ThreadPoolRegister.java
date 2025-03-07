package cn.xk.xcode.core;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import lombok.RequiredArgsConstructor;

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
        DtpRegistry.register(executor, SOURCE_NAME);
    }
}
