package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/8/6 11:00
 * @Version 1.0
 * @Description TraceProperties
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("xk.sys.trace")
@Component
public class TraceProperties
{
    private boolean enable;
}
