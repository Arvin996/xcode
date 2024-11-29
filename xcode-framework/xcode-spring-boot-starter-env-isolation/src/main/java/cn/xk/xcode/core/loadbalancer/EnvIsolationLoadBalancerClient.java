package cn.xk.xcode.core.loadbalancer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.context.EnvTagContextHolder;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/29 8:56
 * @Version 1.0.0
 * @Description EnvIsolationLoadBalancerClient
 **/
@Slf4j
@RequiredArgsConstructor
public class EnvIsolationLoadBalancerClient implements ReactorServiceInstanceLoadBalancer {

    /**
     * 用于获取 serviceId 对应的服务实例的列表
     */
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    /**
     * 需要获取的服务实例名
     */
    private final String serviceId;


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        String tag = EnvTagContextHolder.getTag();
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(list -> getInstanceResponse(list, tag));
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> list, String tag) {
        if (CollectionUtil.isEmpty(list)) {
            log.warn("服务{}的实例为空", serviceId);
            return new EmptyResponse();
        }
        if (StrUtil.isEmpty(tag)) {
            log.warn("服务{}没有匹配tag{}的实例，直接使用所有实例", serviceId, tag);
            return new DefaultResponse(NacosBalancer.getHostByRandomWeight3(list));
        }
        List<ServiceInstance> serviceInstances = CollectionUtil.filterAsList(list, instance -> tag.equals(instance.getMetadata().get("tag")));
        if (CollectionUtil.isEmpty(serviceInstances)) {
            log.warn("服务{}没有匹配tag{}的实例，直接使用所有实例", serviceId, tag);
            serviceInstances = list;
        }
        return new DefaultResponse(NacosBalancer.getHostByRandomWeight3(serviceInstances));
    }
}
