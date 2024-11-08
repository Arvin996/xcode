package cn.xk.xcode.core.annotation;

import cn.xk.xcode.config.TraceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/5 10:00
 * @Version 1.0.0
 * @Description EnableBizTrace
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(TraceImportSelector.class)
public @interface EnableBizTrace {
}
