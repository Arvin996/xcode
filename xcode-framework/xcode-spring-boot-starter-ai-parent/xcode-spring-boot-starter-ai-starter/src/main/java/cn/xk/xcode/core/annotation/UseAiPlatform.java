package cn.xk.xcode.core.annotation;

import cn.xk.xcode.core.enums.AiPlatformEnum;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2025/2/14 13:54
 * @Version 1.0.0
 * @Description UseAiPlatform
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UseAiPlatform {

    AiPlatformEnum mainPlatform() default AiPlatformEnum.NULL;
    AiPlatformEnum chatPlatForm() default AiPlatformEnum.NULL;
    AiPlatformEnum imagePlatForm() default AiPlatformEnum.NULL;
    AiPlatformEnum embeddingPlatForm() default AiPlatformEnum.NULL;
}
