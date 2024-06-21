package cn.xk.xcode.dict.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Author xuk
 * @Date 2024/5/30 15:16
 * @Version 1.0
 * @Description TableDictEvent
 */
public class TableDictEvent extends ApplicationEvent
{
    public TableDictEvent(Object source) {
        super(source);
    }
}
