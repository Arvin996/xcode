package cn.xk.xcode.context;



import cn.xk.xcode.entity.DataTableDict;
import cn.xk.xcode.handler.DataBaseDictHandler;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/5/31 10:38
 * @Version 1.0
 * @Description DictUtils
 */
public class DictContext
{
    public final static Map<String, List<DataTableDict>> GLOBAL_DICT_CACHE= new HashMap<>();

    public static DataTableDict get(String parentId, String code) {
        List<DataTableDict> dataTableDictList = GLOBAL_DICT_CACHE.getOrDefault(parentId, null);
        if (Objects.isNull(dataTableDictList)) {
            return null;
        }
        return dataTableDictList.stream().filter(d -> d.getCode().equals(code)).findFirst().orElse(null);
    }

    public static String getValue(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getName();
    }

    public static String getNote(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getNote();
    }

    public static String getPad(String parentId, String code) {
        DataTableDict dataTableDict = get(parentId, code);
        if (Objects.isNull(dataTableDict)) {
            return null;
        }
        return dataTableDict.getPad();
    }

    public static List<DataTableDict> getListBtParId(String parentId) {
        return GLOBAL_DICT_CACHE.getOrDefault(parentId, new ArrayList<>());
    }

    public static List<String> getListCodeByProperty(String parentId, Function<DataTableDict, String> func) {
        return getListBtParId(parentId).stream().map(func).collect(Collectors.toList());
    }

    public static String getProperty(String parentId, String code, Function<DataTableDict, String> func) {
        return GLOBAL_DICT_CACHE.get(parentId).stream().filter(d -> d.getCode().equals(code)).map(func).findFirst().orElse(null);
    }

}
