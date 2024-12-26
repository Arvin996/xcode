package cn.xk.xcode.handler;

import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/5/30 15:26
 * @Version 1.0
 * @Description DataSourceDictHandler 数据库字典handler
 */
@Slf4j
public class DataSourceDictHandler extends AbstractDataDictHandler {

    private final DataSourceDictLoader dataSourceDictLoader;

    public DataSourceDictHandler(DictCacheStrategy dictCacheStrategy, DataSourceDictLoader dataSourceDictLoader) {
        super(dictCacheStrategy);
        this.dataSourceDictLoader = dataSourceDictLoader;
    }

    @Override
    public void loadDictCache() {
        List<DictDataEntity> list = dataSourceDictLoader.loadDataBaseDict();
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        for (DictDataEntity dictDataEntity : list) {
            dictCacheStrategy.saveOrUpdateDictData(dictDataEntity);
        }
    }
}