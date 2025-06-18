package cn.xk.xcode.core;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.config.SaTokenMemberGlobalConfig;
import cn.xk.xcode.config.SaTokenMixedGlobalConfig;
import cn.xk.xcode.config.SaTokenSystemGlobalConfig;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.PermissionStpType;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/11/22 9:37
 * @Version 1.0.0
 * @Description XcodeSecurityBeanImport
 **/
public class XcodeSecurityBeanImport implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableServerSecurity.class.getName()));
        LoginStpType loginType = (LoginStpType) annotationAttributes.get("loginType");
        PermissionStpType permissionType = (PermissionStpType) annotationAttributes.get("permissionType");
        StpSystemUtil.setStpLogic(new StpLogicJwtForSimple(LoginStpType.SYSTEM.getType()));
        StpMemberUtil.setStpLogic(new StpLogicJwtForSimple(LoginStpType.MEMBER.getType()));
        if (Objects.equals(loginType, LoginStpType.SYSTEM)) {
            SaTokenLoginUtils.setStpLogic(StpSystemUtil.getStpLogic());
        } else if (Objects.equals(loginType, LoginStpType.MEMBER)) {
            SaTokenLoginUtils.setStpLogic(StpMemberUtil.getStpLogic());
        }
        if (Objects.equals(permissionType, PermissionStpType.SYSTEM)) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SaTokenSystemGlobalConfig.class);
            registry.registerBeanDefinition("saTokenSystemGlobalConfig", beanDefinition);
        } else if (Objects.equals(permissionType, PermissionStpType.MEMBER)) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SaTokenMemberGlobalConfig.class);
            registry.registerBeanDefinition("saTokenMemberGlobalConfig", beanDefinition);
        } else {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SaTokenMixedGlobalConfig.class);
            registry.registerBeanDefinition("saTokenMixedGlobalConfig", beanDefinition);
        }
    }
}
