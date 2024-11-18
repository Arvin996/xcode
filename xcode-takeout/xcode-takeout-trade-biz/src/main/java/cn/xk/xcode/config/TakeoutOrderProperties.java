package cn.xk.xcode.config;

import cn.xk.xcode.entity.enums.DelayLevelEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/11/8 9:28
 * @Version 1.0.0
 * @Description TakeoutOrderProperties
 **/
@ConfigurationProperties("xk.takeout.order.timeout")
@Data
@Component
public class TakeoutOrderProperties {

    private DelayLevelEnum delayLevel = DelayLevelEnum.LEVEL14;
}
