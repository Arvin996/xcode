package cn.xk.xcode.config;

import cn.xk.xcode.core.annotation.EnableCrypt;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 14:54
 * @description CryptImportSelector
 */
public class CryptImportSelector implements ImportBeanDefinitionRegistrar{

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 注册bean
        boolean isSign = (boolean) Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableCrypt.class.getName())).getOrDefault("isSign", false);
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(XkSysCryptConfiguration.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("sign", isSign);
        genericBeanDefinition.setPropertyValues(propertyValues);
        registry.registerBeanDefinition("xkSysCryptConfiguration", genericBeanDefinition);
    }
}
