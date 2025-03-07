package cn.xk.xcode.core.factory;

import cn.xk.xcode.core.ThreadPoolRegister;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;

/**
 * @Author xuk
 * @Date 2025/3/7 11:24
 * @Version 1.0.0
 * @Description AbstractThreadPoolFactory
 **/
@RequiredArgsConstructor
public abstract class AbstractThreadPoolFactory {
    public static final String PRE_FIX = "xcode.";
    protected final ThreadPoolRegister register;
    protected static final String DEFAULT_CODE =  "default";

    public abstract ExecutorService createThreadPool(boolean coreThreadRecycle, String threadUniqueCode);

    public ExecutorService createThreadPool(boolean coreThreadRecycle) {
        return createThreadPool(coreThreadRecycle, DEFAULT_CODE);
    }

    public ExecutorService createThreadPool(String threadUniqueCode) {
        return createThreadPool(false, threadUniqueCode);
    }

    public abstract ThreadPoolTypeEnums threadPoolType();
}
