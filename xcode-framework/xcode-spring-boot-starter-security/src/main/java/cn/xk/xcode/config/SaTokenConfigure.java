package cn.xk.xcode.config;

import cn.dev33.satoken.strategy.SaStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;

@Configuration
public class SaTokenConfigure {

    public SaTokenConfigure() {
        rewriteSaStrategy();
    }

    public void rewriteSaStrategy() {
        // 重写Sa-Token的注解处理器，增加注解合并功能 
        SaStrategy.instance.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }
}
