//package cn.xk.xcode.handler;
//
//import cn.xk.xcode.config.DynamicDataSourceRouteDefinitionRepository;
//import cn.xk.xcode.entity.SystemRoutePo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//
/// **
// * @Author xuk
// * @Date 2025/2/11 9:50
// * @Version 1.0.0
// * @Description DynamicRouteHandler
// **/
//@RequiredArgsConstructor
//public class DynamicRouteHandler {
//
//    private final DynamicDataSourceRouteDefinitionRepository routeDefinitionRepository;
//
//    public Mono<ServerResponse> addRoute(ServerRequest request) {
//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(routeDefinitionRepository.saveRoute(request.bodyToMono(SystemRoutePo.class)), Map.class);
//    }
//
//    public Mono<ServerResponse> deleteRoute(ServerRequest request) {
//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(routeDefinitionRepository.deleteRoute(Mono.just("route-" + request.pathVariable("id"))), Map.class);
//    }
//
//    public Mono<ServerResponse> updateRoute(ServerRequest request) {
//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(routeDefinitionRepository.saveRoute(request.bodyToMono(SystemRoutePo.class)), Map.class);
//    }
//}
