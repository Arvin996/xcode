package cn.xk.xcode.filter;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.entity.GwAccessLog;
import cn.xk.xcode.utils.WebFrameworkUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_MS_FORMATTER;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/28 19:57
 * @description
 */
@Component
@Slf4j
public class AccessLogFilter implements GlobalFilter, Ordered {

    @Resource
    private CodecConfigurer codecConfigurer;

    private void writeAccessLog(GwAccessLog gatewayLog){
        // todo 方式1：使用消息队列，记录到数据库中 后续会做一个统一的日志服务

        // 方式2 控制台输出
        Map<String, Object> values = MapUtil.newHashMap(15, true); // 手工拼接，保证排序；15 保证不用扩容
        values.put("userId", gatewayLog.getUserId());
        values.put("grantType", gatewayLog.getGrantType());
        values.put("routeId", gatewayLog.getRoute() != null ? gatewayLog.getRoute().getId() : null);
        values.put("schema", gatewayLog.getSchema());
        values.put("requestUrl", gatewayLog.getRequestUrl());
        values.put("queryParams", gatewayLog.getQueryParams().toSingleValueMap());
        values.put("requestBody", JSONUtil.isTypeJSON(gatewayLog.getRequestBody()) ? // 保证 body 的展示好看
                JSONUtil.parse(gatewayLog.getRequestBody()) : gatewayLog.getRequestBody());
        values.put("requestHeaders", JSONUtil.toJsonStr(gatewayLog.getRequestHeaders().toSingleValueMap()));
        values.put("userIp", gatewayLog.getUserIp());
        values.put("responseBody", JSONUtil.isTypeJSON(gatewayLog.getResponseBody()) ? // 保证 body 的展示好看
                JSONUtil.parse(gatewayLog.getResponseBody()) : gatewayLog.getResponseBody());
        values.put("responseHeaders", gatewayLog.getResponseHeaders() != null ?
                JSONUtil.toJsonStr(gatewayLog.getResponseHeaders().toSingleValueMap()) : null);
        values.put("httpStatus", gatewayLog.getHttpStatus());
        values.put("startTime", LocalDateTimeUtil.format(gatewayLog.getStartTime(), NORM_DATETIME_MS_FORMATTER));
        values.put("endTime", LocalDateTimeUtil.format(gatewayLog.getEndTime(), NORM_DATETIME_MS_FORMATTER));
        values.put("duration", gatewayLog.getDuration() != null ? gatewayLog.getDuration() + " ms" : null);
        log.info("[writeAccessLog][网关日志：{}]", JSONUtil.toJsonStr(values));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!StpUtil.isLogin()){
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        // TODO traceId 可以直接设置到请求头或者前端传递
        // todo 根据ip 得到用户地址
        GwAccessLog gwAccessLog = new GwAccessLog();
        gwAccessLog.setRoute(exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR));
        gwAccessLog.setSchema(request.getURI().getScheme());
        gwAccessLog.setRequestMethod(request.getMethodValue());
        gwAccessLog.setRequestUrl(request.getURI().getRawPath());
        gwAccessLog.setQueryParams(request.getQueryParams());
        gwAccessLog.setRequestHeaders(request.getHeaders());
        gwAccessLog.setStartTime(LocalDateTime.now());
        gwAccessLog.setUserIp(WebFrameworkUtils.getClientIP(exchange));
        MediaType mediaType = request.getHeaders().getContentType();
        // 处理json数据
        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)
                || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)){
            return filterWithRequestBody(exchange, chain, gwAccessLog);
        }
        return filterWithoutRequestBody(exchange, chain, gwAccessLog);
    }

    private Mono<Void> filterWithoutRequestBody(ServerWebExchange exchange, GatewayFilterChain chain, GwAccessLog accessLog) {
        // 包装 Response，用于记录 Response Body
        ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, accessLog);
        return chain.filter(exchange.mutate().response(decoratedResponse).build())
                .then(Mono.fromRunnable(() -> writeAccessLog(accessLog))); // 打印日志
    }

    private Mono<Void> filterWithRequestBody(ServerWebExchange exchange, GatewayFilterChain chain, GwAccessLog accessLog) {
        // 包装 Response，用于记录 Response Body 解解决响应体分段传输问题。
        ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, accessLog);
        return chain.filter(exchange.mutate().response(decoratedResponse).build())
                .then(Mono.fromRunnable(() -> writeAccessLog(accessLog))); // 打印日志
    }

    private ServerHttpResponseDecorator recordResponseLog(ServerWebExchange exchange, GwAccessLog gatewayLog) {
        ServerHttpResponse response = exchange.getResponse();
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    // 计算执行时间
                    gatewayLog.setEndTime(LocalDateTime.now());
                    gatewayLog.setDuration((int) (LocalDateTimeUtil.between(gatewayLog.getStartTime(),
                            gatewayLog.getEndTime()).toMillis()));
                    // 设置其它字段
                    gatewayLog.setUserId(StpUtil.getLoginIdAsString());
                    gatewayLog.setGrantType((String) StpUtil.getExtra("grantType"));
                    // todo 用户类型可以考虑在system模块增加字段
              //      gatewayLog.setUserType(StpUtil.getExtra("userType").toString());
                    gatewayLog.setResponseHeaders(response.getHeaders());
                    gatewayLog.setHttpStatus(response.getStatusCode());

                    // 获取响应类型，如果是 json 就打印
                    String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    if (StringUtils.isNotBlank(originalResponseContentType)
                            && originalResponseContentType.contains("application/json")) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 合并多个流集合，解决返回体分段传输 防止内容丢失
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            // 设置 response body 到网关日志
                            byte[] content = readContent(dataBuffers);
                            String responseResult = new String(content, StandardCharsets.UTF_8);
                            gatewayLog.setResponseBody(responseResult);

                            // 响应
                            return bufferFactory.wrap(content);
                        }));
                    }
                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };

    }

    private byte[] readContent(List<? extends DataBuffer> dataBuffers) {
        // 合并多个流集合，解决返回体分段传输
        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        DataBuffer join = dataBufferFactory.join(dataBuffers);
        byte[] content = new byte[join.readableByteCount()];
        join.read(content);
        // 释放掉内存
        DataBufferUtils.release(join);
        return content;
    }

    // 最高开始 记录日志
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
