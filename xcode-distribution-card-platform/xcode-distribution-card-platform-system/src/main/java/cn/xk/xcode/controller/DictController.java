package cn.xk.xcode.controller;

import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemDictDataService;
import cn.xk.xcode.service.SystemDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class DictController {

    @Resource
    private SystemDictTypeService systemDictTypeService;

    @Resource
    private SystemDictDataService systemDictDataService;

    @Resource
    private DictCacheStrategy dictCacheStrategy;

    @PostMapping("/queryAll")
    @Operation(summary = "查询所有字典")
    public CommonResult<List<SysDictDataVo>> queryAllDict(@RequestBody QueryDictDto queryDictDto) {
        return CommonResult.success(systemDictTypeService.selectAllDictDate(queryDictDto));
    }
}
