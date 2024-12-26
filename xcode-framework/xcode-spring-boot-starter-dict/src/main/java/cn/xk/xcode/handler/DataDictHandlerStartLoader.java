package cn.xk.xcode.handler;

import cn.hutool.extra.spring.SpringUtil;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/5/30 15:41
 * @Version 1.0
 * @Description DataDictHandlerStartLoader
 */
public class DataDictHandlerStartLoader {
    public static void startLoad(){
        Map<String, AbstractDataDictHandler> abstractDataDictHandlerMap = SpringUtil.getBeansOfType(AbstractDataDictHandler.class);
        abstractDataDictHandlerMap.values().forEach(
                AbstractDataDictHandler::loadDictCache
        );
    }
}
