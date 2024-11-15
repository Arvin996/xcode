package cn.xk.xcode.core.annotation;

import cn.xk.xcode.config.client.MonitorClientImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/13 10:46
 * @Version 1.0.0
 * @Description EnableMonitorClient 开启监控数据上送注解
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({SchedulingConfiguration.class, MonitorClientImportSelector.class})
public @interface EnableMonitorClient {
}
