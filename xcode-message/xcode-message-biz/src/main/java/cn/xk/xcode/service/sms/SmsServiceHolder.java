package cn.xk.xcode.service.sms;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/3/13 14:30
 * @Version 1.0.0
 * @Description SmsServiceHolder
 **/
@Component
public class SmsServiceHolder {

    private final Map<SmsPlatEnum, ISmsService> smsServiceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        SpringUtil.getBeansOfType(ISmsService.class).values().forEach(smsService -> {
            smsServiceMap.put(smsService.type(), smsService);
        });
    }

    public ISmsService getSmsService(SmsPlatEnum smsPlatEnum) {
        return smsServiceMap.get(smsPlatEnum);
    }
}
