package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.UseAiPlatform;
import cn.xk.xcode.core.context.XcodeAiPlatformHelper;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2025/2/14 14:33
 * @Version 1.0.0
 * @Description AiPlatformInterceptor
 **/
@Slf4j
public class AiPlatformInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed;
        try {
            beforeExec(invocation);
            proceed = invocation.proceed();
            return proceed;
        } finally {
            XcodeAiPlatformHelper.remove();
        }
    }

    private void beforeExec(MethodInvocation invocation) {
        try{
            Method method = invocation.getMethod();
            Object targetObject = invocation.getThis();
            Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();
            UseAiPlatform useAiPlatform = AnnotationUtils.getAnnotation(method, UseAiPlatform.class);
            if (Objects.isNull(useAiPlatform)){
                useAiPlatform = AnnotationUtils.getAnnotation(clazz, UseAiPlatform.class);
            }
            if (Objects.isNull(useAiPlatform)){
                return;
            }
            AiPlatformEnum mainPlatform = useAiPlatform.mainPlatform();
            AiPlatformEnum chatPlatForm = useAiPlatform.chatPlatForm();
            AiPlatformEnum imagePlatForm = useAiPlatform.imagePlatForm();
            AiPlatformEnum embeddingPlatForm = useAiPlatform.embeddingPlatForm();
            if (mainPlatform != AiPlatformEnum.NULL){
                XcodeAiPlatformHelper.useAllAiPlatform(mainPlatform);
            }
            if (chatPlatForm != AiPlatformEnum.NULL){
                XcodeAiPlatformHelper.useAiChatPlatform(chatPlatForm);
            }
            if (imagePlatForm != AiPlatformEnum.NULL){
                XcodeAiPlatformHelper.useAiImagePlatform(imagePlatForm);
            }
            if (embeddingPlatForm != AiPlatformEnum.NULL){
                XcodeAiPlatformHelper.useAiEmbeddingPlatform(embeddingPlatForm);
            }
        }catch (Exception e){
            log.error("AiPlatformInterceptor异常, 异常信息{}", e.getMessage());
        }
    }
}
