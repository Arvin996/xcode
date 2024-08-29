package cn.xk.xcode.core.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import cn.xk.xcode.support.enums.TransEnumConfigurer;
import cn.xk.xcode.support.enums.TransEnumsRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

import static cn.xk.xcode.constants.TransFlexGlobalConstants.CONFIGURER_MUST_UNIQUE;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 10:11
 * @description
 */
@Slf4j
public class InitTransEnumsEventHandler {

    private InitTransEnumsEventHandler(){}

    public static GlobalEnumsContext init() {
        log.info("开始注册globalEnumsContext");
        Map<String, TransEnumConfigurer> beansOfType = SpringUtil.getBeansOfType(TransEnumConfigurer.class);
        TransEnumsRegistry transEnumsRegistry = new TransEnumsRegistry(new ArrayList<>());
        if (beansOfType.size() > 1) {
            throw new ServiceException(CONFIGURER_MUST_UNIQUE);
        }
        TransEnumConfigurer configurer;
        if (beansOfType.isEmpty()) {
            log.info("未配置TransEnumConfigurer");
        }else {
            configurer = beansOfType.values().iterator().next();
            configurer.registry(transEnumsRegistry);
        }
        log.info("注册globalEnumsContext完成");
        return new GlobalEnumsContext(transEnumsRegistry);
    }

}
