package cn.xk.xcode.handler.core;

import cn.xk.xcode.context.DictContext;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_HANDLER_NOT_EXISTS;

/**
 * @Author xuk
 * @Date 2024/8/2 14:44
 * @Version 1.0
 * @Description CheckCodeAdvisor
 */
@RequiredArgsConstructor
public class CheckCodeAdvisor
{
    private final Map<String, CheckCodeHandlerStrategy> checkCodeHandlerStrategyMap;

    public CheckCodeHandlerStrategy getCheckCodeHandlerStrategy(String type){
        CheckCodeHandlerStrategy checkCodeHandlerStrategy = checkCodeHandlerStrategyMap.get(type);
        if (Objects.isNull(checkCodeHandlerStrategy)){
            throw new ServiceException(CHECK_CODE_HANDLER_NOT_EXISTS);
        }
        return checkCodeHandlerStrategy;
    }
}
