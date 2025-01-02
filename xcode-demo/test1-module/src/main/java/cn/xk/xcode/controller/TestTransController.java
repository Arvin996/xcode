package cn.xk.xcode.controller;

import cn.xk.xcode.core.annotation.AutoFlexTrans;
import cn.xk.xcode.core.annotation.IgnoreTrans;
import cn.xk.xcode.entity.trans.*;
import cn.xk.xcode.entity.po.BizMessagePo;
import cn.xk.xcode.mapper.BizMessageMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/12/27 11:16
 * @Version 1.0.0
 * @Description TestTransController
 **/
@RestController
@AutoFlexTrans
public class TestTransController {

    @Resource
    private BizMessageMapper bizMessageMapper;


    @PostMapping("/trans0")
    public CommonResult<FlexTransTestEntity3> trans0(){
        FlexTransTestEntity3 flexTransTestEntity = new FlexTransTestEntity3();
        flexTransTestEntity.setId1("aaa");
        flexTransTestEntity.setId(CollectionUtil.createSingleList(12321222345L));
        return CommonResult.success(flexTransTestEntity);
    }

    @PostMapping("/trans1")
    public CommonResult<FlexTransTestEntity> trans1(){
        FlexTransTestEntity flexTransTestEntity = new FlexTransTestEntity();
        flexTransTestEntity.setId(CollectionUtil.createSingleList(12321222345L));
        return CommonResult.success(flexTransTestEntity);
    }

    @PostMapping("/trans2")
    public CommonResult<BizMessagePo> trans2(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", 12321222345L);
        return CommonResult.success(bizMessageMapper.selectOneByMap(map));
    }

    @PostMapping("/trans3")
    public CommonResult<FlexTransTestEntity1> trans3(){
        FlexTransTestEntity1 flexTransTestEntity = new FlexTransTestEntity1();
        flexTransTestEntity.setId(12321222345L);
        return CommonResult.success(flexTransTestEntity);
    }

    @PostMapping("/trans4")
    public CommonResult<FlexTransTestEntity2> trans4(){
        FlexTransTestEntity2 flexTransTestEntity = new FlexTransTestEntity2();
        flexTransTestEntity.setId(12321222345L);
        return CommonResult.success(flexTransTestEntity);
    }

    @IgnoreTrans
    @PostMapping("/trans5")
    public CommonResult<FlexTransTestEntity4> trans5(){
        FlexTransTestEntity4 flexTransTestEntity = new FlexTransTestEntity4();
        FlexTransTestEntity2 flexTransTestEntity2 = new FlexTransTestEntity2();
        flexTransTestEntity2.setId(12321222345L);
        flexTransTestEntity.setFlexTransTestEntity2List(CollectionUtil.createSingleList(flexTransTestEntity2));
        FlexTransTestEntity3 flexTransTestEntity3 = new FlexTransTestEntity3();
        flexTransTestEntity3.setId1("aaa");
        flexTransTestEntity3.setId(CollectionUtil.createSingleList(12321222345L));
        flexTransTestEntity.setFlexTransTestEntity3(flexTransTestEntity3);
        flexTransTestEntity.setId1("aaa");
        return CommonResult.success(flexTransTestEntity);
    }


}
