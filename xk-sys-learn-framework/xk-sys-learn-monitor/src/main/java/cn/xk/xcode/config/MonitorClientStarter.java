package cn.xk.xcode.config;

import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.message.BaseMessage;
import cn.xk.xcode.message.MessageTopicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/11/13 13:33
 * @Version 1.0.0
 * @Description MonitorClientStarter
 **/
@RequiredArgsConstructor
@Slf4j
public class MonitorClientStarter implements ApplicationRunner, EnvironmentAware {

    private static final int MONITOR_MESSAGE_RETRY_TIMES = 3;
    private static final String MONITOR_KEY = "monitor";
    private static final ErrorCode MONITOR_REGISTER_MESSAGE_SEND_ERROR = new IntErrorCode(1300_0_500, "发送服务监控注册信息失败");

    @Resource
    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    private Environment environment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 发送服务注册信息
        register();
    }

    public void register() {
        String applicationName = environment.getProperty("spring.application.name");
        String actuatorPath = environment.getProperty("manage.endpoints.web.base-path", "actuator");
        MonitorMessage monitorMessage = new MonitorMessage()
                .setActuatorPath(actuatorPath)
                .setApplicationName(applicationName);
        monitorMessage.setBizKey(MONITOR_KEY);
        monitorMessage.setMessageSource(applicationName);
        monitorMessage.setSendTime(LocalDateTime.now());
        monitorMessage.setRetryTimes(MONITOR_MESSAGE_RETRY_TIMES);
        rocketMQEnhanceTemplate.sendAsync(MessageTopicType.MONITOR_TOPIC.getType(), monitorMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送服务监控注册信息成功， 应用名：{}， 监听路径：{}， 消息id:{}", applicationName, actuatorPath, sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable throwable) {
                log.error("发送服务监控注册信息失败， 应用名：{}， 监听路径：{}, 异常信息：{}", applicationName, actuatorPath, throwable.getMessage());
                throw new ServiceException(MONITOR_REGISTER_MESSAGE_SEND_ERROR);
            }
        });
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    public static class MonitorMessage extends BaseMessage {
        private String applicationName;
        private String actuatorPath;
    }
}
