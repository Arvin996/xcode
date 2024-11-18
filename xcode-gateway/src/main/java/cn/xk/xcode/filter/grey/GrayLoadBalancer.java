package cn.xk.xcode.filter.grey;

import cn.hutool.core.collection.CollUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/28 20:52
 * @description 灰度发布负载均衡器
 * 根据请求的 header[version] 匹配，
 * 筛选满足 metadata[version] 相等的服务实例列表，然后随机 + 权重进行选择一个
 * 假如请求的 header[version] 为空，则不进行筛选，所有服务实例都进行选择
 * 如果 metadata[version] 都不相等，则不进行筛选，所有服务实例都进行选择
 * 考虑到实现的简易，使用 Nacos 的 nacos.weight，基于 {@link NacosBalancer} 筛选。
 */
@RequiredArgsConstructor
@Slf4j
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    // header中的版本号
    private static final String VERSION = "version";

    // 需要获取的服务实例名
    private final String serviceId;

     // 用于获取 serviceId 对应的服务实例的列表
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        // 获得 HttpHeaders 属性，实现从 header 中获取 version
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        // 选择实例
        return supplier.get(request).next().map(list -> getInstanceResponse(list, headers));
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, HttpHeaders headers){
        if (CollectionUtils.isEmpty(instances)){
            log.warn("[getInstanceResponse][serviceId({}) 服务实例列表为空]", serviceId);
            return new EmptyResponse();
        }
        // 筛选满足 version 条件的实例列表
        String version = headers.getFirst(VERSION);
        List<ServiceInstance> chooseInstances;
        if (!StringUtils.hasLength(version)){
            // 说明不需要筛选
            chooseInstances = instances;
        }else {
            // 筛选
            chooseInstances = CollectionUtil.convertList(instances, Function.identity()
                    , instance -> version.equals(instance.getMetadata().get("version")));
            if (CollectionUtils.isEmpty(chooseInstances)){
                log.warn("[getInstanceResponse][serviceId({}) 没有满足版本({})的服务实例列表，直接使用所有服务实例列表]", serviceId, version);
                chooseInstances = instances;
            }
        }

        // 基于 tag 过滤实例列表
        chooseInstances = filterTagServiceInstances(chooseInstances, headers);
        // 随机 + 权重获取实例列表 TODO 目前直接使用 Nacos 提供的方法 也可自己实现
        return new DefaultResponse(NacosBalancer.getHostByRandomWeight3(chooseInstances));
    }

    private List<ServiceInstance> filterTagServiceInstances(List<ServiceInstance> chooseInstances, HttpHeaders headers) {
        String tag = headers.getFirst("tag");
        if (!StringUtils.hasLength(tag)){
            return chooseInstances;
        }
        List<ServiceInstance> instances = CollectionUtil.convertList(chooseInstances, Function.identity(), instance -> tag.equals(instance.getMetadata().get("tag")));
        if (CollUtil.isEmpty(chooseInstances)) {
            log.warn("[filterTagServiceInstances][serviceId({}) 没有满足 tag({}) 的服务实例列表，直接使用所有服务实例列表]", serviceId, tag);
            instances = chooseInstances;
        }
        return instances;
    }
}
