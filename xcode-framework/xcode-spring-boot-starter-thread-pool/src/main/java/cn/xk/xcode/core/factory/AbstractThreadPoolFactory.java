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

    public abstract ExecutorService createThreadPool(boolean coreThreadRecycle, String threadUniqueCode);

    public abstract ThreadPoolTypeEnums threadPoolType();
}
