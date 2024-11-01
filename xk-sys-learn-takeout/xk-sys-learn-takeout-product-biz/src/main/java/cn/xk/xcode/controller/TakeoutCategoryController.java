package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
