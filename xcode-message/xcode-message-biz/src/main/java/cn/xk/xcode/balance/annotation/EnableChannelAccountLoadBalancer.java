package cn.xk.xcode.balance.annotation;

import cn.xk.xcode.balance.core.ChannelAccountLoadBalancerImportSelector;
import cn.xk.xcode.enums.LoadBalancerEnum;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2025/3/11 14:16
 * @Version 1.0.0
 * @Description ChannelAccountLoadBalancer
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ChannelAccountLoadBalancerImportSelector.class)
public @interface EnableChannelAccountLoadBalancer {
    LoadBalancerEnum loadBalancerStrategy() default LoadBalancerEnum.RANDOM;
}
