package cn.xk.xcode.core;

import cn.xk.xcode.event.StateChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/7/2 14:46
 * @Version 1.0
 * @Description WebSocketLifeCycle
 */
@Component
public class WebSocketLifeCycle implements SmartLifecycle
{
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    private boolean running = false;
    @Override
    public void start() {
        // 启动时的操作
        applicationEventPublisher.publishEvent(new StateChangeEvent(new Object()));
        running = true;
    }

    @Override
    public void stop() {
        applicationEventPublisher.publishEvent(new StateChangeEvent(new Object()));
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public int getPhase() {
        // 返回值越小，越先执行stop()方法
        return Integer.MAX_VALUE;
    }
}
