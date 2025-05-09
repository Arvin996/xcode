package cn.xk.xcode.balance.core;

import cn.xk.xcode.balance.annotation.EnableChannelAccountLoadBalancer;
import cn.xk.xcode.enums.LoadBalancerEnum;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
* @Author xuk
* @Date 2025/3/11 14:36
* @Version 1.0.0
* @Description ChannelAccountLoadBalancerImportSelector
**/
public class ChannelAccountLoadBalancerImportSelector implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableChannelAccountLoadBalancer.class.getName());
        LoadBalancerEnum loadBalancerEnum = (LoadBalancerEnum) annotationAttributes.get("loadBalancerStrategy");
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        switch (loadBalancerEnum){
            case RANDOM:
                genericBeanDefinition.setBeanClass(RandomLoadBalancer.class);
                registry.registerBeanDefinition("randomLoadBalancer", genericBeanDefinition);
                break;
            case WEIGHT:
                genericBeanDefinition.setBeanClass(WeightedLoadBalancer.class);
                registry.registerBeanDefinition("weightLoadBalancer", genericBeanDefinition);
                break;
            case ROUND:
                genericBeanDefinition.setBeanClass(RoundLoadBalancer.class);
                registry.registerBeanDefinition("roundLoadBalancer", genericBeanDefinition);
                break;
            default:
                genericBeanDefinition.setBeanClass(FirstLoadBalancer.class);
                registry.registerBeanDefinition("firstLoadBalancer", genericBeanDefinition);
                break;
        }
    }
}
