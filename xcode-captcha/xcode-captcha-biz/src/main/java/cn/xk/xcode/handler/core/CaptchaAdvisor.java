package cn.xk.xcode.handler.core;

import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_HANDLER_NOT_EXISTS;

/**
 * @Author xuk
 * @Date 2024/8/2 14:44
 * @Version 1.0
 * @Description CaptchaAdvisor
 */
@RequiredArgsConstructor
public class CaptchaAdvisor {
    private final Map<String, CaptchaHandlerStrategy> captchaHandlerStrategyMap;

    public CaptchaHandlerStrategy getCaptchaHandlerStrategy(String type){
        CaptchaHandlerStrategy captchaHandlerStrategy = captchaHandlerStrategyMap.get(type);
        if (Objects.isNull(captchaHandlerStrategy)){
            throw new ServiceException(CHECK_CODE_HANDLER_NOT_EXISTS);
        }
        return captchaHandlerStrategy;
    }
}
