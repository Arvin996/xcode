package cn.xk.xcode.core.task;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.MonitorServerProperties;
import cn.xk.xcode.core.monitor.MonitorClientInstance;
import cn.xk.xcode.core.monitor.MonitorClientInstanceContext;
import cn.xk.xcode.utils.MonitorClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/11/14 13:36
 * @Version 1.0.0
 * @Description MonitorHeartbeatTask
 **/
@Slf4j
@RequiredArgsConstructor
public class MonitorHeartbeatTask {

    private final MonitorServerProperties monitorServerProperties;
    private static final String HEARTBEAT_PATH = "/health";

    @Scheduled(fixedDelayString = "#{monitorServerProperties.heartbeatInterval}", timeUnit = TimeUnit.SECONDS)
    public void heartbeat() {
        ConcurrentHashMap<String, MonitorClientInstance> instanceMap = MonitorClientInstanceContext.getInstanceMap();
        instanceMap.values().forEach(instance -> {
            HttpResponse httpResponse = HttpRequest.get(buildClientHeartbeatUrl(instance)).timeout(monitorServerProperties.getHeartBeatTimeout()).execute();
            if (!httpResponse.isOk()) {
                log.error("心跳检测服务:{}, ip: {}, 端口号：{}", instance.getApplicationName(), instance.getIp(), instance.getPort());
                // 非健康状态
                instance.setStatus("DOWN");
                instance.setLastUpdateTime(LocalDateTime.now());
            } else {
                String body = httpResponse.body();
                JSONObject healthBody = JSONUtil.parseObj(body);
                String health = healthBody.getStr("health");
                if (StringUtils.equalsIgnoreCase(instance.getStatus(), health)) {
                    instance.setLastUpdateTime(LocalDateTime.now());
                } else {
                    instance.setStatus(health);
                    instance.setLastUpdateTime(LocalDateTime.now());
                }
            }
        });
    }

    private String buildClientHeartbeatUrl(MonitorClientInstance instance) {
        String ip = instance.getIp();
        String port = instance.getPort();
        String webContextPath = instance.getWebContextPath();
        String actuatorPath = instance.getActuatorPath();
        StringBuilder stringBuilder = new StringBuilder("http://");
        if (StringUtils.equalsIgnoreCase("ipv6", instance.getIpType())) {
            stringBuilder.append(MonitorClientUtil.iNetIPv6Utils.normalizeIPv6(ip));
        } else {
            stringBuilder.append(ip);
        }
        stringBuilder.append(":").append(port);
        if (!StrUtil.isEmpty(webContextPath)) {
            stringBuilder.append(webContextPath);
        }
        stringBuilder.append(actuatorPath).append(HEARTBEAT_PATH);
        return stringBuilder.toString();
    }

}
