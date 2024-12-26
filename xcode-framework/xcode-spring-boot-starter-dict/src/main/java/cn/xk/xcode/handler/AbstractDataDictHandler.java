package cn.xk.xcode.handler;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.event.DictDataReloadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @Author xuk
 * @Date 2024/5/30 15:19
 * @Version 1.0
 * @Description AbstractDataDictHandler
 */
@Slf4j
public abstract class AbstractDataDictHandler implements ApplicationListener<DictDataReloadEvent> {

    protected final DictCacheStrategy dictCacheStrategy;

    public AbstractDataDictHandler(DictCacheStrategy dictCacheStrategy) {
        this.dictCacheStrategy = dictCacheStrategy;
    }

    @Override
    public void onApplicationEvent(DictDataReloadEvent event) {
        reloadDictCache();
    }

    /**
     * 重新加载缓存
     */
    public void reloadDictCache() {
        dictCacheStrategy.clearCache();
        log.info("数据库字典缓存刷新");
        loadDictCache();
    }

    public abstract void loadDictCache();
}
