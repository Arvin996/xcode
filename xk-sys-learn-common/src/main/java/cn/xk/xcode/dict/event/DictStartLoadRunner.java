package cn.xk.xcode.dict.event;

import cn.xk.xcode.dict.handler.DataBaseDictHandler;
import cn.xk.xcode.dict.handler.DataDictHandlerStartLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/5/30 15:17
 * @Version 1.0
 * @Description DictStartLoadRunner
 */
@Component
@Order(3)
@ConditionalOnBean(DataBaseDictHandler.class)
@Slf4j
public class DictStartLoadRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        DataDictHandlerStartLoader.startLoad();
        log.info("字典缓存加载成功");
    }
}
