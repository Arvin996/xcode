package cn.xk.xcode.annotation;


import cn.xk.xcode.config.MonitorServerImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/14 9:03
 * @Version 1.0.0
 * @Description EnableMonitorServer
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({SchedulingConfiguration.class, MonitorServerImportSelector.class})
@EnableScheduling
public @interface EnableMonitorServer {
}
