package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Administrator
 * @Date 2024/8/26 10:42
 * @Version V1.0.0
 * @Description WebThreadPoolTaskProperties
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("xk.web.thread.pool")
public class WebThreadPoolTaskProperties {

    private int corePoolSize = Runtime.getRuntime().availableProcessors();

    private int maxPoolSize = 2 * Runtime.getRuntime().availableProcessors();

    private int queueCapacity = 200;

    private int keepAliveSeconds = 60;

}
