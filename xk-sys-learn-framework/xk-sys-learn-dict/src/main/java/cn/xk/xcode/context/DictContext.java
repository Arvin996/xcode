package cn.xk.xcode.context;



import cn.xk.xcode.entity.DataTableDict;
import cn.xk.xcode.handler.DataBaseDictHandler;

import java.util.List;
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
    private final DataBaseDictHandler dataBaseDictHandler;

    public DictContext(DataBaseDictHandler dataBaseDictHandler){
        this.dataBaseDictHandler = dataBaseDictHandler;
    }

    public String getValue(String parentId, String code){
        return dataBaseDictHandler.getValue(parentId, code);
    }

    public String getNote(String parentId, String code){
        return dataBaseDictHandler.getNote(parentId, code);
    }

    public String getPad(String parentId, String code){
        return dataBaseDictHandler.getPad(parentId, code);
    }

    public String getValue(String code){
        return getValue("##", code);
    }

    public String getNote(String code){
        return getNote("##", code);
    }

    public String getPad(String code){
        return getPad("##", code);
    }

    public List<DataTableDict> getListBtParId(String parentId){
        return dataBaseDictHandler.getListBtParId(parentId);
    }

    public List<String> getListCodeByProperty(String parentId, Function<DataTableDict, String> func){
        return getListBtParId(parentId).stream().map(func).collect(Collectors.toList());
    }

    public String getProperty(String parentId, String code, Function<DataTableDict, String> func){
        return dataBaseDictHandler.getProperty(parentId, code, func);
    }

}
