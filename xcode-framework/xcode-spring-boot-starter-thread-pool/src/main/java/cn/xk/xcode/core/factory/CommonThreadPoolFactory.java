package cn.xk.xcode.core.factory;

import cn.xk.xcode.config.ThreadPoolConstants;
import cn.xk.xcode.core.ThreadPoolRegister;
import org.dromara.dynamictp.common.em.QueueTypeEnum;
import org.dromara.dynamictp.common.em.RejectedTypeEnum;
import org.dromara.dynamictp.core.executor.DtpExecutor;
import org.dromara.dynamictp.core.support.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2025/3/7 11:29
 * @Version 1.0.0
 * @Description CommonThreadPoolFactory
 **/
public class CommonThreadPoolFactory extends AbstractThreadPoolFactory {

    public CommonThreadPoolFactory(ThreadPoolRegister register) {
        super(register);
    }

    @Override
    public ExecutorService createThreadPool(boolean coreThreadRecycle, String threadUniqueCode) {
        DtpExecutor dtpExecutor = ThreadPoolBuilder.newBuilder()
                .threadPoolName(PRE_FIX + threadUniqueCode)
                .corePoolSize(ThreadPoolConstants.COMMON_CORE_POOL_SIZE)
                .maximumPoolSize(ThreadPoolConstants.COMMON_MAX_POOL_SIZE)
                .keepAliveTime(ThreadPoolConstants.COMMON_KEEP_LIVE_TIME)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                .allowCoreThreadTimeOut(coreThreadRecycle)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), coreThreadRecycle ? ThreadPoolConstants.BIG_QUEUE_SIZE : ThreadPoolConstants.COMMON_QUEUE_SIZE, false)
                .buildDynamic();
        if (!coreThreadRecycle) {
            register.register(dtpExecutor);
        }
        return dtpExecutor;
    }

    @Override
    public ThreadPoolTypeEnums threadPoolType() {
        return ThreadPoolTypeEnums.COMMON;
    }
}
