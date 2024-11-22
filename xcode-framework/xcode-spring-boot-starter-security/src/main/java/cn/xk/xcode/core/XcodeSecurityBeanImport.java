package cn.xk.xcode.core;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.config.SaTokenMemberGlobalConfig;
import cn.xk.xcode.config.SaTokenSystemGlobalConfig;
import cn.xk.xcode.pojo.StpType;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

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
        StpType stpType = (StpType) Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableServerSecurity.class.getName())).get("type");
        if (Objects.equals(stpType, StpType.SYSTEM)){
            StpSystemUtil.setStpLogic(new StpLogicJwtForSimple(stpType.getType()));
            SaTokenLoginUtils.setStpLogic(StpSystemUtil.getStpLogic());
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SaTokenSystemGlobalConfig.class);
            registry.registerBeanDefinition("saTokenSystemGlobalConfig", beanDefinition);
        }else {
            StpMemberUtil.setStpLogic(new StpLogicJwtForSimple(stpType.getType()));
            SaTokenLoginUtils.setStpLogic(StpMemberUtil.getStpLogic());
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SaTokenMemberGlobalConfig.class);
            registry.registerBeanDefinition("saTokenMemberGlobalConfig", beanDefinition);
        }
    }
}
