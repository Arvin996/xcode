package cn.xk.xcode.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author xuk
 * @Date 2024/7/2 14:48
 * @Version 1.0
 * @Description StateChangeEvent
 */
public class StateChangeEvent extends ApplicationEvent {

    public StateChangeEvent(Object source) {
        super(source);
    }
}
