package cn.xk.xcode.handler;


import cn.xk.xcode.entity.DictDataEntity;

import java.util.Collections;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/5/30 15:37
 * @Version 1.0
 * @Description DefaultDataSourceLoader
 */
public class DefaultDataSourceLoader implements DataSourceDictLoader{

    @Override
    public List<DictDataEntity> loadDataBaseDict() {
        return Collections.emptyList();
    }
}
