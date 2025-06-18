package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.category.AddCategoryDto;
import cn.xk.xcode.entity.dto.category.UpdateCategoryDto;
import cn.xk.xcode.entity.vo.category.ProductCategoryVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/5 11:32
 * @Version 1.0.0
 * @Description ProductCategoryController
 **/
@RestController
@RequestMapping("/manage/product/category")
@Tag(name = "ProductCategoryController", description = "ProductCategoryController 商品分类controller")
public class ProductCategoryController {

    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("/addProductCategory")
    @Operation(summary = "添加商品分类")
    public CommonResult<Boolean> addProductCategory(@Validated @RequestBody AddCategoryDto addCategoryDto) {
        return CommonResult.success(productCategoryService.addProductCategory(addCategoryDto));
    }

    @PostMapping("/updateProductCategory")
    @Operation(summary = "更新商品分类")
    public CommonResult<Boolean> updateProductCategory(@Validated @RequestBody UpdateCategoryDto updateCategoryDto) {
        return CommonResult.success(productCategoryService.updateProductCategory(updateCategoryDto));
    }

    @DeleteMapping("/delProductCategory/{categoryId}")
    @Operation(summary = "删除商品分类")
    public CommonResult<Boolean> delProductCategory(@PathVariable("categoryId") Long categoryId) {
        return CommonResult.success(productCategoryService.delProductCategory(categoryId));
    }

    @GetMapping("/queryProductCategory")
    @Operation(summary = "查询商品分类")
    public CommonResult<List<ProductCategoryVo>> queryProductCategory() {
        return CommonResult.success(productCategoryService.queryProductCategory());
    }
}
