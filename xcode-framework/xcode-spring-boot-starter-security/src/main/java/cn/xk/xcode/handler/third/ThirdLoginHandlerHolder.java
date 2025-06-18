package cn.xk.xcode.handler.third;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/6/10 9:33
 * @Version 1.0.0
 * @Description ThirdLoginHandlerHolder
 **/
@Component
public class ThirdLoginHandlerHolder {

    private final Map<String, AbstractThirdLoginHandler> thirdLoginHandlerMap = new HashMap<>();

    @Autowired
    public ThirdLoginHandlerHolder(List<AbstractThirdLoginHandler> abstractThirdLoginHandlerList) {
        for (AbstractThirdLoginHandler abstractThirdLoginHandler : abstractThirdLoginHandlerList) {
            thirdLoginHandlerMap.put(abstractThirdLoginHandler.getThirdLoginType().getType(), abstractThirdLoginHandler);
        }
    }

    public AbstractThirdLoginHandler routeThirdLoginHandler(String thirdLoginType) {
        return thirdLoginHandlerMap.get(thirdLoginType);
    }
}
