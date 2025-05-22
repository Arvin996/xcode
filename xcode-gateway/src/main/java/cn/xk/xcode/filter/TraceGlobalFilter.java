//package cn.xk.xcode.filter;
//
//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.StrUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @Author xuk
// * @Date 2024/12/18 10:30
// * @Version 1.0.0
// * @Description TraceGlobalFilter
// **/
//@Component
//public class TraceGlobalFilter implements GlobalFilter, Ordered {
//
//    private static final String TRACE_ID = "traceId";
//    private static final String TRACE_SORT = "traceSort";
//    private static final String CALL_SERVICE = "callService";
//    @Value("${spring.application.name}")
//    private String serviceName;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 1. 获取请求
//        ServerHttpRequest request = exchange.getRequest();
//        // 2. 获取请求头
//        String traceId = request.getHeaders().getFirst(TRACE_ID);
//        if (StrUtil.isEmpty(traceId)){
//            // 自动生成一个traceId
//            request = request.mutate().header(TRACE_ID, IdUtil.fastSimpleUUID())
//                    .header(TRACE_SORT, "1")
//                    .header(CALL_SERVICE, serviceName)
//                    .build();
//        }else {
//            // 如果请求头中有traceId，则将traceId传递给下游服务
//            request = request.mutate().header(TRACE_ID, traceId)
//                    .header(TRACE_SORT, "1")
//                    .header(CALL_SERVICE, serviceName)
//                    .build();
//        }
//        return chain.filter(exchange.mutate().request(request).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return Integer.MAX_VALUE;
//    }
//}
