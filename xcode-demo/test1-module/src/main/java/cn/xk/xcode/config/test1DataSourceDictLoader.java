package cn.xk.xcode.config;

import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.handler.DataSourceDictLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/26 15:33
 * @Version 1.0.0
 * @Description test1DataSourceDictLoader
 **/
@Component
public class test1DataSourceDictLoader implements DataSourceDictLoader {
    @Override
    public List<DictDataEntity> loadDataBaseDict() {
        List<DictDataEntity> list = new ArrayList<>();
        list.add(DictDataEntity.builder().dictType("aa").code("aaa").name("aaa").build());
        list.add(DictDataEntity.builder().dictType("bb").code("bbb").name("bbb") .build());
        list.add(DictDataEntity.builder().dictType("cc").code("ccc").name("ccc").build());
        return list;
    }
}
