package cn.xk.xcode.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.util.SaResult;
//import cn.xk.xcode.handler.DynamicRouteHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/2/11 8:33
 * @Version 1.0.0
 * @Description XcodeGateWayConfiguration
 **/
@EnableConfigurationProperties(XcodeGateWayProperties.class)
@Configuration
public class XcodeGateWayConfiguration {

    @Resource
    private XcodeGateWayProperties xcodeGateWayProperties;

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.notMatch("/");
                    //     SaRouter.match("/**", "/user/doLogin", r -> StpUtil.checkLogin());

                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> SaResult.error(e.getMessage()));
    }

//    @Bean
//    public DynamicDataSourceRouteDefinitionRepository getDynamicDateSourceRouteDefinitionRepository() {
//        return new DynamicDataSourceRouteDefinitionRepository(xcodeGateWayProperties);
//    }

//    @Bean
//    RouterFunction<ServerResponse> dynamicRouteHandler(DynamicRouteHandler handler) {
//        return RouterFunctions.nest(RequestPredicates.path("/route"),
//                RouterFunctions.route(RequestPredicates.GET("/del/{id}"), handler::deleteRoute)
//                        .andRoute(RequestPredicates.POST("/update").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::updateRoute)
//                        .andRoute(RequestPredicates.POST("/add").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::addRoute)
//
//        );
//    }

//    @Bean
//    public DynamicRouteHandler dynamicRouteHandler(DynamicDataSourceRouteDefinitionRepository routeDefinitionRepository) {
//        return new DynamicRouteHandler(routeDefinitionRepository);
//    }
}
