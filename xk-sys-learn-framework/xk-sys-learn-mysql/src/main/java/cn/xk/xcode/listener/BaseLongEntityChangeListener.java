package cn.xk.xcode.listener;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.SetListener;
import com.mybatisflex.annotation.UpdateListener;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/10/29 14:52
 * @Version 1.0.0
 * @Description BaseLongEntityChangeListener
 **/
@Slf4j
public class BaseLongEntityChangeListener implements InsertListener, UpdateListener, SetListener {

    @Override
    public void onInsert(Object o) {
        setPropertyIfPresent(o, "createTime", LocalDateTime.now());
        setPropertyIfPresent(o, "createUser", StpUtil.getLoginIdAsLong());
    }

    @Override
    public Object onSet(Object o, String s, Object o1) {
        return o1;
    }

    @Override
    public void onUpdate(Object o) {
        setPropertyIfPresent(o, "updateTime", LocalDateTime.now());
        setPropertyIfPresent(o, "updateUser", StpUtil.getLoginIdAsLong());
    }

    private void setPropertyIfPresent(Object object, String propertyName, Object propertyValue) {
        try {
            // 获取对象的 Class 对象
            Class<?> clazz = object.getClass();
            // 获取对象的字段
            Field field = clazz.getDeclaredField(propertyName);
            // 设置字段为可访问，以便访问私有字段
            field.setAccessible(true);
            // 设置字段的值
            field.set(object, propertyValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 如果字段不存在，则忽略异常
            log.warn(" Fill EntityChangeField failed; Property {} not found. ", propertyName);
        }
    }
}
