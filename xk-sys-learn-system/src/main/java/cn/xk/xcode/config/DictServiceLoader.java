package cn.xk.xcode.config;
import cn.xk.xcode.convert.DictConvert;
import cn.xk.xcode.dict.entity.DataTableDict;
import cn.xk.xcode.dict.handler.DataBaseDictLoader;
import cn.xk.xcode.service.DictService;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/6/21 10:09
 * @Version 1.0
 * @Description DictServiceImpl
 */
@Component
public class DictServiceLoader implements DataBaseDictLoader {

    @Resource
    private DictService dictService;

    @Resource
    private DictConvert dictConvert;

    @Override
    public List<DataTableDict> loadDataBaseDict() {
        return dictConvert.dictDoToTableDict(dictService.list());
    }
}
