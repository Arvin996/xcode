package cn.xk.xcode.core.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;

/**
 * @Author xuk
 * @Date 2024/11/29 10:02
 * @Version 1.0.0
 * @Description EnvIsolationLoadBalancerFactory
 **/
public class EnvIsolationLoadBalancerFactory extends LoadBalancerClientFactory {

    public EnvIsolationLoadBalancerFactory(LoadBalancerClientsProperties properties) {
        super(properties);
    }

    @Override
    public ReactiveLoadBalancer<ServiceInstance> getInstance(String serviceId) {
        return new EnvIsolationLoadBalancerClient(super.getLazyProvider(serviceId, ServiceInstanceListSupplier.class),
                serviceId);
    }
}
