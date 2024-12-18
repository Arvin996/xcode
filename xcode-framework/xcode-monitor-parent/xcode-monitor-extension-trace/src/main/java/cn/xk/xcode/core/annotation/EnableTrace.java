package cn.xk.xcode.core.annotation;

import cn.xk.xcode.config.TraceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/18 15:36
 * @Version 1.0.0
 * @Description EnableTrace
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(TraceImportSelector.class)
public @interface EnableTrace {
}
