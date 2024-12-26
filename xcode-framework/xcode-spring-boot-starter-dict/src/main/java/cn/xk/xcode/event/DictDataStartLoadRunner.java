package cn.xk.xcode.event;

import cn.xk.xcode.handler.DataDictHandlerStartLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @Author xuk
 * @Date 2024/5/30 15:17
 * @Version 1.0
 * @Description DictStartLoadRunner
 */
@Slf4j
public class DictDataStartLoadRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        DataDictHandlerStartLoader.startLoad();
        log.info("字典缓存加载成功");
    }
}
