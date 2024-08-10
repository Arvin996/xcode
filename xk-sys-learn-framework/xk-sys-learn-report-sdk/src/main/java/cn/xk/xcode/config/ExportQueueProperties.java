package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/8/8 10:48
 * @Version 1.0
 * @Description ExportQueueProperties
 */
@Data
@Component
@ConfigurationProperties("xk.sys.export.queue")
public class ExportQueueProperties {
    private int coreSize;
}
