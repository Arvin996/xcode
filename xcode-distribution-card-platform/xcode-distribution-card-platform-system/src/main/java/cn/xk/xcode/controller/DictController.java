package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.dict.*;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemDictDataService;
import cn.xk.xcode.service.SystemDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/9 9:52
 * @Version 1.0.0
 * @Description DictController
 **/
@RequestMapping("/manage/dict")
@RestController
@Tag(name = "DictController", description = "字典接口")
@Validated
public class DictController {

    @Resource
    private SystemDictTypeService systemDictTypeService;

    @Resource
    private SystemDictDataService systemDictDataService;

    @PostMapping("/queryAll")
    @Operation(summary = "查询所有字典")
    public CommonResult<List<SysDictDataVo>> queryAllDict(@RequestBody QueryDictDto queryDictDto) {
        return CommonResult.success(systemDictTypeService.selectAllDictDate(queryDictDto));
    }

    @PostMapping("/addDictType")
    @Operation(summary = "添加字典类型")
    public CommonResult<Boolean> addDictType(@RequestBody AddDictTypeDto addDictTypeDto) {
        return CommonResult.success(systemDictTypeService.addDictType(addDictTypeDto));
    }

    @PostMapping("/addDictData")
    @Operation(summary = "添加字典数据")
    public CommonResult<Boolean> addDictData(@RequestBody AddDictDataDto addDictDataDto) {
        return CommonResult.success(systemDictDataService.addDictData(addDictDataDto));
    }

    @DeleteMapping("/delDictType/{id}")
    @Operation(summary = "删除字典类型")
    public CommonResult<Boolean> delDictType(@PathVariable("id") Long id) {
        return CommonResult.success(systemDictTypeService.delDictType(id));
    }

    @DeleteMapping("/delDictData/{id}")
    @Operation(summary = "删除字典数据")
    public CommonResult<Boolean> delDictData(@PathVariable("id") Long id) {
        return CommonResult.success(systemDictDataService.delDictData(id));
    }

    @PostMapping("/updateDictType")
    @Operation(summary = "修改字典类型")
    public CommonResult<Boolean> updateDictType(@RequestBody UpdateDictTypeDto updateDictTypeDto) {
        return CommonResult.success(systemDictTypeService.updateDictType(updateDictTypeDto));
    }


    @PostMapping("/updateDictData")
    @Operation(summary = "修改字典数据")
    public CommonResult<Boolean> updateDictData(@RequestBody UpdateDictDataDto updateDictDataDto) {
        return CommonResult.success(systemDictDataService.updateDictData(updateDictDataDto));
    }

}
