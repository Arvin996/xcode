package cn.xk.xcode.core;

import cn.xk.xcode.core.factory.AbstractThreadPoolFactory;
import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import cn.xk.xcode.core.factory.ThreadPoolTypeEnums;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author xuk
 * @Date 2025/3/7 11:12
 * @Version 1.0.0
 * @Description ThreadPoolExecutorHolder
 **/
@RequiredArgsConstructor
public class ThreadPoolExecutorHolder {

    private final ThreadPoolRegister register;
    private final ThreadPoolExecutorsUniqueCodeLoader loader;
    private final ThreadPoolProduceDecider decider;

    @PostConstruct
    public void init() {
        List<String> uniqueCodes = loader.loadUniqueCode();
        if (CollectionUtil.isEmpty(uniqueCodes)) {
            return;
        }
        for (String code : uniqueCodes) {
            ExecutorService executorService = decider.decide(ThreadPoolTypeEnums.COMMON, code);
            register.register((DtpExecutor) executorService);
        }

    }

    public ExecutorService routeThreadPool(String threadUniqueCode) {
        return DtpRegistry.getExecutor(AbstractThreadPoolFactory.PRE_FIX + threadUniqueCode);
    }
}
