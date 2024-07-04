package cn.xk.xcode.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.entity.DataTableDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/5/30 15:26
 * @Version 1.0
 * @Description DataBaseDictHandler
 */
@Slf4j
public class DataBaseDictHandler extends AbstractDataDictHandler {

    DataBaseDictLoader dataBaseDictLoader = new DefaultDataBaseDictLoader();

    public DataBaseDictHandler() {
        super();
    }

    @Override
    void loadDictCache() {
        Map<String, DataBaseDictLoader> beanOfType = SpringUtil.getBeansOfType(DataBaseDictLoader.class);
        if (beanOfType.isEmpty()) {
            cache.put("##", dataBaseDictLoader.loadDataBaseDict());
        } else {
            dataBaseDictLoader = beanOfType.values().iterator().next();
            List<DataTableDict> dataTableDictList = dataBaseDictLoader.loadDataBaseDict();
            dataTableDictList.forEach(d -> {
                String parId = d.getParId();
                if (Objects.isNull(parId) || parId.isEmpty()) {
                    parId = "##";
                }
                List<DataTableDict> innerDataTableList = cache.get(parId);
                if (Objects.isNull(innerDataTableList)) {
                    innerDataTableList = new ArrayList<>();
                }
                innerDataTableList.add(d);
                cache.put(parId, innerDataTableList);
            });
        }
    }

}