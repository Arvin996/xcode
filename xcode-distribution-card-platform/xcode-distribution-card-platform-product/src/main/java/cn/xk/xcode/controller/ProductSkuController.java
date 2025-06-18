package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.sku.AddSkuDto;
import cn.xk.xcode.entity.dto.sku.UpdateSkuDto;
import cn.xk.xcode.entity.vo.sku.ProductSkuVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.ProductSkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/5 8:45
 * @Version 1.0.0
 * @Description ProductSkuController
 **/
@RestController
@RequestMapping("/api/product/sku")
@Tag(name = "ProductSkuController", description = "ProductSkuController 商品sku controller")
public class ProductSkuController {

    @Resource
    private ProductSkuService productSkuService;

    @Operation(summary = "添加商品sku")
    @PostMapping("/addProductSku")
    public CommonResult<Boolean> addProductSku(@Validated @RequestBody AddSkuDto addSkuDto) {
        return CommonResult.success(productSkuService.addProductSku(addSkuDto));
    }

    @Operation(summary = "更新商品sku")
    @PostMapping("/updateProductSku")
    public CommonResult<Boolean> updateProductSku(@Validated @RequestBody UpdateSkuDto updateSkuDto) {
        return CommonResult.success(productSkuService.updateProductSku(updateSkuDto));
    }

    @Operation(summary = "删除商品sku")
    @DeleteMapping("/delProductSku/{skuId}")
    public CommonResult<Boolean> delProductSku(@PathVariable("skuId") Long skuId) {
        return CommonResult.success(productSkuService.delProductSku(skuId));
    }

    @Operation(summary = "查询商品sku")
    @GetMapping("/queryProductSku/{spuId}")
    public CommonResult<List<ProductSkuVo>> queryProductSku(@PathVariable("spuId") Long spuId) {
        return CommonResult.success(productSkuService.queryProductSku(spuId));
    }
}
