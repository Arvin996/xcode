package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.QueryCategoryDto;
import cn.xk.xcode.entity.dto.category.TakeoutCategoryBaseDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.CategoryResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/1 16:07
 * @Version 1.0.0
 * @Description TakeoutCategoryController
 **/
@RestController
@RequestMapping("/takeout/category")
public class TakeoutCategoryController {

    @Resource
    private TakeoutCategoryService takeoutCategoryService;

    @PostMapping("/add")
    @Operation(summary = "新增分类")
    @SaCheckPermission("takeout:category:add")
    public CommonResult<Boolean> addCategory(@Validated @RequestBody AddCategoryDto addCategoryDto){
        return CommonResult.success(takeoutCategoryService.addCategory(addCategoryDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除分类")
    @SaCheckPermission("takeout:category:del")
    public CommonResult<Boolean> delCategory(@Validated @RequestBody TakeoutCategoryBaseDto takeoutCategoryBaseDto){
        return CommonResult.success(takeoutCategoryService.delCategory(takeoutCategoryBaseDto));
    }

    @PostMapping("/update")
    @Operation(summary = "更新分类")
    @SaCheckPermission("takeout:category:update")
    public CommonResult<Boolean> updateCategory(@Validated @RequestBody UpdateCategoryDto updateCategoryDto){
        return CommonResult.success(takeoutCategoryService.updateCategory(updateCategoryDto));
    }

    @PostMapping("/list")
    @Operation(summary = "获取分类")
    @SaCheckPermission("takeout:category:get")
    public CommonResult<List<CategoryResultVo>> getCategoryList(@RequestBody QueryCategoryDto queryCategoryDto){
        return CommonResult.success(takeoutCategoryService.getCategoryList(queryCategoryDto));
    }
}
