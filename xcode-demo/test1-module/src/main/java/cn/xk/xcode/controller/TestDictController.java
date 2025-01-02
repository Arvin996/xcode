package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.DictTrans;
import cn.xk.xcode.annotation.RefreshDict;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.entity.dict.DictTestEntity1;
import cn.xk.xcode.entity.dict.DictTestEntity2;
import cn.xk.xcode.entity.dict.UpdateDictEntity;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/26 15:35
 * @Version 1.0.0
 * @Description TestDictController
 **/
@RestController
@DictTrans
public class TestDictController {

    @Autowired(required = false)
    private DictCacheStrategy dictCacheStrategy;

    @PostMapping("/dict1")
    public CommonResult<String> dict1(@RequestBody Map<String, Object> map){
        String dictValue = dictCacheStrategy.getDictName("aaa", "aa");
        return CommonResult.success(dictValue);
    }

    @PostMapping("/dict2")
    public CommonResult<String> dict2(@RequestBody Map<String, Object> map){
        String dictValue = dictCacheStrategy.getDictName("ddd", "enum");
        return CommonResult.success(dictValue);
    }
    @PostMapping("/dict3")
    public CommonResult<DictTestEntity1> dict3(@RequestBody Map<String, Object> map){
        DictTestEntity1 dictTestEntity1 = new DictTestEntity1();
        dictTestEntity1.setCode("ddd");
        return CommonResult.success(dictTestEntity1);
    }

    @PostMapping("/dict4")
    public CommonResult<DictTestEntity1> dict4(@RequestBody Map<String, Object> map){
        DictTestEntity1 dictTestEntity1 = new DictTestEntity1();
        dictTestEntity1.setCode("ddd");
        DictTestEntity2 dictTestEntity2 = new DictTestEntity2();
        dictTestEntity2.setCode("ddd");
        DictTestEntity2 dictTestEntity3 = new DictTestEntity2();
        dictTestEntity3.setCode("ddd");
        dictTestEntity1.setList(Arrays.asList(dictTestEntity2, dictTestEntity3));
        return CommonResult.success(dictTestEntity1);
    }

    @PostMapping("/dict5")
    @RefreshDict(dictType = "#updateDictEntity.dictType"
            , dictCode = "#updateDictEntity.code"
            , dictName = "#updateDictEntity.name"
            , dictDesc = "#updateDictEntity.desc")
    public CommonResult<DictTestEntity1> dict5(@RequestBody UpdateDictEntity updateDictEntity){
        return dict4(new HashMap<>());
    }

}
