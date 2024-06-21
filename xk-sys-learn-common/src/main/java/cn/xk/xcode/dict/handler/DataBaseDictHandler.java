package cn.xk.xcode.dict.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.dict.entity.DataTableDict;
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
@Component
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

    public DataTableDict get(String parentId, String code) {
        List<DataTableDict> dataTableDictList = cache.getOrDefault(parentId, null);
        if (Objects.isNull(dataTableDictList)) {
            return null;
        }
        return dataTableDictList.stream().filter(d -> d.getCode().equals(code)).findFirst().orElse(null);
    }

    public String getValue(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getName();
    }

    public String getNote(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getNote();
    }

    public String getPad(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getPad();
    }

    public List<DataTableDict> getListBtParId(String parentId) {
        return cache.getOrDefault(parentId, new ArrayList<>());
    }

    public List<String> getListCodeByProperty(String parentId, Function<DataTableDict, String> func) {
        return getListBtParId(parentId).stream().map(func).collect(Collectors.toList());
    }

    public String getProperty(String parentId, String code, Function<DataTableDict, String> func) {
        return cache.get(parentId).stream().filter(d -> d.getCode().equals(code)).map(func).findFirst().orElse(null);
    }

}