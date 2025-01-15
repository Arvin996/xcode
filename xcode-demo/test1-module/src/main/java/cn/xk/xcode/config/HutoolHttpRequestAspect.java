package cn.xk.xcode.config;

import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.core.support.proxy.AbstractThirdRequestAspect;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/1/14 16:55
 * @Version 1.0.0
 * @Description HutoolHttpRequestAspect
 **/
@Component
public class HutoolHttpRequestAspect extends AbstractThirdRequestAspect {

    public HutoolHttpRequestAspect(RocketMQEnhanceTemplate rocketMQEnhanceTemplate) {
        super(rocketMQEnhanceTemplate);
    }

    @Override
    public boolean isSuccess(Object var1) {
        return true;
    }
}
