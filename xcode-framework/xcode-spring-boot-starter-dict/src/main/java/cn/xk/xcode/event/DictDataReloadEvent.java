package cn.xk.xcode.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author xuk
 * @Date 2024/5/30 15:16
 * @Version 1.0
 * @Description TableDictEvent
 */
public class DictDataReloadEvent extends ApplicationEvent {

    public DictDataReloadEvent(Object source) {
        super(source);
    }
}
