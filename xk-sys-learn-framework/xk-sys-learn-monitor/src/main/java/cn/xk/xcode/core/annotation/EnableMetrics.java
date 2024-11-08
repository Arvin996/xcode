package cn.xk.xcode.core.annotation;

import cn.xk.xcode.config.MetricsImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/5 11:06
 * @Version 1.0.0
 * @Description EnableMetrics
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(MetricsImportSelector.class)
public @interface EnableMetrics {
}
