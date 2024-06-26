package cn.xk.xcode.handler;

import cn.xk.xcode.entity.DataTableDict;
import cn.xk.xcode.event.TableDictEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.*;

/**
 * @Author xuk
 * @Date 2024/5/30 15:19
 * @Version 1.0
 * @Description AbstractDataDictHandler
 */
@Slf4j
public abstract class AbstractDataDictHandler implements ApplicationListener<TableDictEvent> {

    protected Map<String, List<DataTableDict>> cache;

    public AbstractDataDictHandler() {
        cache = new HashMap<>();
    }

    @Override
    public void onApplicationEvent(TableDictEvent event) {
        reloadDictCache();
    }

    /**
     * 功能描述: 重新加载缓存 <br/>
     */
    public void reloadDictCache() {
        cache.clear();
        log.info("数据库字典缓存刷新");
        loadDictCache();
    }

    protected void put(String parentId, DataTableDict dataTableDict) {
        List<DataTableDict> dataTableDictList = cache.getOrDefault(parentId, null);
        if (Objects.isNull(dataTableDictList)) {
            List<DataTableDict> list = new ArrayList<>();
            list.add(dataTableDict);
            cache.put(parentId, list);
        } else {
            dataTableDictList.add(dataTableDict);
        }
    }

    protected void remove(String parentId, String code) {
        List<DataTableDict> dataTableDictList = cache.getOrDefault(parentId, null);
        if (Objects.isNull(dataTableDictList)) {
            return;
        }
        dataTableDictList.removeIf(dataTableDict -> dataTableDict.getCode().equals(code));
    }

    abstract void loadDictCache();
}
