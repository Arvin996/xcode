package cn.xk.xcode.config;

import cn.hutool.core.map.MapUtil;
import cn.xk.xcode.entity.SystemRoutePo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2025/2/11 8:36
 * @Version 1.0.0
 * @Description DynamicDateSourceRoute
 **/
@Slf4j
@RequiredArgsConstructor
public class DynamicDataSourceRouteDefinitionRepository implements RouteDefinitionRepository {

    private final XcodeGateWayProperties xcodeGateWayProperties;
    private static final ConcurrentHashMap<String, RouteDefinition> routeDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routeDefinitionMap.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            routeDefinitionMap.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(r -> {
            routeDefinitionMap.remove(r);
            return Mono.empty();
        });
    }

    public Mono<Map<String, Object>> deleteRoute(Mono<String> routeId){
        return routeId.map(r -> {
            routeDefinitionMap.remove(r);
            return MapUtil.of("msg", "success");
        });
    }


    public Mono<Map<String, Object>> saveRoute(Mono<SystemRoutePo> systemRoutePoMono){
        // 帮我们自动生成路由
        return systemRoutePoMono.map(systemRoutePo -> {
            String routePrefix = xcodeGateWayProperties.getRoutePrefix();
            RouteDefinition definition = new RouteDefinition();
            definition.setId("route-" + systemRoutePo.getId());
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName("Path");
            Map<String, String> predicates = new HashMap<>();
            if (StringUtils.isEmpty(routePrefix)){
                predicates.put("_genkey_0", systemRoutePo.getServiceName() + "/**");
            }else {
                predicates.put("_genkey_0", xcodeGateWayProperties.getRoutePrefix() + "/" + systemRoutePo.getServiceName() + "/**");
            }
            predicateDefinition.setArgs(predicates);
            definition.setPredicates(Collections.singletonList(predicateDefinition));
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName("StripPrefix");
            Map<String, String> filters = new HashMap<>();
            filters.put("_genkey_0", "2");
            filterDefinition.setArgs(filters);
            definition.setFilters(Collections.singletonList(filterDefinition));
            definition.setUri(URI.create("lb://" + systemRoutePo.getServiceName() + (StringUtils.isEmpty(systemRoutePo.getRoute()) ? "" : "/" + systemRoutePo.getRoute())));
            routeDefinitionMap.putIfAbsent(definition.getId(), definition);
            return MapUtil.of("msg", "success");
        });
    }

    @PostConstruct
    public void init() {
        List<SystemRoutePo> systemRoutePos = new ArrayList<>();
        for (SystemRoutePo systemRoutePo : systemRoutePos) {
            saveRoute(Mono.just(systemRoutePo)).subscribe(s ->
                    log.info("服务{} 路由{} 已经添加", systemRoutePo.getServiceName(), systemRoutePo.getRoute()));
        }
    }
}
