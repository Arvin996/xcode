package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.MonitorClientProperties;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.MonitorClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/14 10:16
 * @Version 1.0.0
 * @Description MonitorClientStartLoader 启动时像服务端注册信息
 **/
@Slf4j
@RequiredArgsConstructor
public class MonitorClientStartLoader implements ApplicationRunner, EnvironmentAware, DisposableBean {

    private final MonitorClientProperties monitorClientProperties;
    private Environment environment;
    private static final String METRICS_PATH = "/metrics";
    private static final String SERVER_REGISTER_PATH = "/register";
    public static final String SERVICE_UPDATE_PATH = "/update";
    public static final String MONITOR_PATH = "/monitor";
    private static final String HEARTBEAT_PATH = "/health";
    public static final ErrorCode MONITOR_ADDR_IS_EMPTY = new IntErrorCode(1300_0_504, "监控服务端地址不能为空");
    public static final ErrorCode MONITOR_REGISTER_FAIL = new IntErrorCode(1300_0_505, "注册服务监控失败, 请检查服务端是否开启，以及地址{}是否正确");
    public static final ErrorCode GET_METRICS_DATA_FAIL = new IntErrorCode(1300_0_506, "获取本机metrics数据失败");
    public static final ErrorCode MONITOR_UPDATE_FAIL = new IntErrorCode(1300_0_507, "更新服务监控失败, 请检查服务端是否开启，以及地址{}是否正确");
    public static final ErrorCode GET_HEALTH_DATA_FAIL = new IntErrorCode(1300_0_508, "获取本机HEALTH状态失败");
    private static final int HEALTH_TIMEOUT_TIME = 1;

    @Override
    public void run(ApplicationArguments args) {
        registerService(monitorClientProperties, environment, SERVER_REGISTER_PATH);
    }

    public static void registerService(MonitorClientProperties monitorClientProperties, Environment environment, String path) {
        MonitorClientInstance monitorClientInstance = getMonitorClientInstance(monitorClientProperties, environment);
        String healthUrl = buildHealthUrl(environment);
        HttpResponse httpResponse = HttpRequest.get(healthUrl).timeout(HEALTH_TIMEOUT_TIME).execute();
        if (!httpResponse.isOk()) {
            // 非健康状态
            ExceptionUtil.castServerException(GET_HEALTH_DATA_FAIL);
        } else {
            String body = httpResponse.body();
            JSONObject healthBody = JSONUtil.parseObj(body);
            String health = healthBody.getStr("health");
            monitorClientInstance.setStatus(health);
        }
        // 发送本机的metrics数据到服务端
        httpResponse = HttpRequest.post(buildMonitorServerRegisterAddr(monitorClientProperties, path))
                .charset("UTF-8")
                .body(JSONUtil.toJsonStr(monitorClientInstance))
                .contentType("application/json")
                .execute();
        if (!httpResponse.isOk()) {
            if (path.equals(SERVER_REGISTER_PATH)) {
                ExceptionUtil.castServerException(MONITOR_REGISTER_FAIL, monitorClientProperties.getMonitorServerAddr());
            } else {
                ExceptionUtil.castServerException(MONITOR_UPDATE_FAIL, monitorClientProperties.getMonitorServerAddr());
            }
        }
        if (path.equals(SERVER_REGISTER_PATH)) {
            log.info("注册服务监控成功, 注册信息:{}", monitorClientInstance);
        } else {
            log.info("更新服务监控成功, 更新信息:{}", monitorClientInstance);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static String buildMetricsPath(MonitorClientInstance monitorClientInstance) {
        StringBuilder stringBuilder = new StringBuilder("http://127.0.0.1:");
        stringBuilder.append(monitorClientInstance.getPort());
        if (!StrUtil.isEmpty(monitorClientInstance.getWebContextPath())) {
            stringBuilder.append(monitorClientInstance.getWebContextPath());
        }
        stringBuilder.append(monitorClientInstance.getActuatorPath());
        stringBuilder.append(METRICS_PATH);
        return stringBuilder.toString();
    }

    public static String buildMonitorServerRegisterAddr(MonitorClientProperties monitorClientProperties, String path) {
        StringBuilder stringBuilder = new StringBuilder("http://");
        stringBuilder.append(monitorClientProperties.getMonitorServerAddr());
        if (!StrUtil.isEmpty(monitorClientProperties.getMonitorServerContextPath())) {
            stringBuilder.append(monitorClientProperties.getMonitorServerContextPath());
        }
        stringBuilder.append(MONITOR_PATH);
        stringBuilder.append(path);
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {
        // 下线
        MonitorClientInstance monitorClientInstance = getMonitorClientInstance(monitorClientProperties, environment);
        HttpResponse response;
        monitorClientInstance.setStatus("DOWN");
        // 发送本机的metrics数据到服务端
        response = HttpRequest.post(buildMonitorServerRegisterAddr(monitorClientProperties, SERVICE_UPDATE_PATH))
                .charset("UTF-8")
                .body(JSONUtil.toJsonStr(monitorClientInstance))
                .contentType("application/json")
                .execute();
        if (!response.isOk()) {
            ExceptionUtil.castServerException(MONITOR_REGISTER_FAIL, monitorClientProperties.getMonitorServerAddr());
        }
        log.info("服务自动下线监控成功, 更新信息:{}", monitorClientInstance);
    }

    private static MonitorClientInstance getMonitorClientInstance(MonitorClientProperties monitorClientProperties, Environment environment) {
        String monitorServerAddr = monitorClientProperties.getMonitorServerAddr();
        if (StrUtil.isEmpty(monitorServerAddr)) {
            ExceptionUtil.castServerException(MONITOR_ADDR_IS_EMPTY);
        }
        MonitorClientInstance monitorClientInstance = MonitorClientUtil.bulidMonitorClientInstance(monitorClientProperties);
        String actuatorPath = environment.getProperty("management.endpoints.web.base-path", "/actuator");
        String port = environment.getProperty("server.port", "8080");
        String applicationName = environment.getProperty("spring.application.name", "");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        monitorClientInstance.setActuatorPath(actuatorPath);
        monitorClientInstance.setPort(port);
        monitorClientInstance.setApplicationName(applicationName);
        monitorClientInstance.setWebContextPath(contextPath);
        // 获取本机的metrics数据
        String metricsPath = buildMetricsPath(monitorClientInstance);
        HttpResponse response = HttpRequest.get(metricsPath).execute();
        if (!response.isOk()) {
            ExceptionUtil.castServerException(GET_METRICS_DATA_FAIL);
        }
        String metaData = response.body();
        if (StrUtil.isEmpty(metaData)) {
            ExceptionUtil.castServerException(GET_METRICS_DATA_FAIL);
        }
        // 获取详细的输出
        List<String> metricsNames = JSONUtil.parseObj(metaData).getBeanList("names", String.class);
        JSONObject jsonObject = new JSONObject();
        for (String name : metricsNames) {
            response = HttpRequest.get(metricsPath + "/" + name).execute();
            if (!response.isOk()) {
                ExceptionUtil.castServerException(GET_METRICS_DATA_FAIL);
            }
            jsonObject.append(name, JSONUtil.parseObj(response.body()));
        }
        monitorClientInstance.setMetaData(jsonObject);
        return monitorClientInstance;
    }

    private static String buildHealthUrl(Environment environment) {
        String actuatorPath = environment.getProperty("management.endpoints.web.base-path", "/actuator");
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        StringBuilder stringBuilder = new StringBuilder("http://127.0.0.1");
        stringBuilder.append(":").append(port);
        if (!StrUtil.isEmpty(contextPath)) {
            stringBuilder.append(contextPath);
        }
        stringBuilder.append(actuatorPath).append(HEARTBEAT_PATH);
        return stringBuilder.toString();
    }
}
