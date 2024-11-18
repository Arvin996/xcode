package cn.xk.xcode.core;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.AutoRegisterController;
import cn.xk.xcode.annotation.EnableRegisterController;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/11/1 13:55
 * @Version 1.0.0
 * @Description RegisterControllerImportCryptImportSelector
 **/
public class RegisterControllerImportSelector implements ImportBeanDefinitionRegistrar{


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!importingClassMetadata.hasAnnotation(EnableRegisterController.class.getName())) {
            return;
        }
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableRegisterController.class.getName());
        String basePackage = (String) annotationAttributes.get("basePackage");
        if (StrUtil.isBlankIfStr(basePackage)) {
            // 设置为默认的包名
            basePackage = ClassUtils.getPackageName(importingClassMetadata.getClassName());
        }
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, AutoRegisterController.class);
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(RegisterControllerConfigure.class);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, classes);
        genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        registry.registerBeanDefinition("registerControllerConfigure", genericBeanDefinition);
    }
}
