package cn.xk.xcode.config;
import cn.xk.xcode.convert.DictConvert;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.handler.DataSourceDictLoader;
import cn.xk.xcode.service.DictService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/6/21 10:09
 * @Version 1.0
 * @Description DictServiceImpl
 */
@Configuration
public class DictServiceLoader implements DataSourceDictLoader {

    @Resource
    private DictService dictService;

    @Resource
    private DictConvert dictConvert;

    @Override
    public List<DictDataEntity> loadDataBaseDict() {
        return dictConvert.dictDoToTableDict(dictService.list());
    }
}
