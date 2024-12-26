package cn.xk.xcode.handler;

import cn.xk.xcode.entity.DictDataEntity;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/5/30 15:27
 * @Version 1.0
 * @Description DataBaseDictLoader 数据库字段读取接口
 */
public interface DataSourceDictLoader {
    List<DictDataEntity> loadDataBaseDict();
}
