package cn.xk.xcode.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/5/30 15:15
 * @Version 1.0
 * @Description DataTableReloadPublisher
 */
@Component
public class DictDataReloadPublisher {

    @Resource
    private ApplicationContext applicationContext;

    public void publish() {
        applicationContext.publishEvent(new DictDataReloadEvent(this));
    }
}
