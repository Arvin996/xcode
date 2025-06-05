package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.spu.AddSpuDto;
import cn.xk.xcode.entity.dto.spu.QuerySpuDto;
import cn.xk.xcode.entity.dto.spu.UpdateSpuDto;
import cn.xk.xcode.entity.vo.spu.ProductSpuVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.ProductSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/6/4 14:24
 * @Version 1.0.0
 * @Description ProductSpuController
 **/
@RestController
@RequestMapping("/api/product/spu")
@Tag(name = "ProductSpuController", description = "ProductSpuController 商品spu controller")
public class ProductSpuController {

    @Resource
    private ProductSpuService productSpuService;

    @PostMapping("/addProductSpu")
    @Operation(summary = "添加商品spu")
    public CommonResult<Boolean> addProductSpu(@Validated @RequestBody AddSpuDto addSpuDto) {
        return CommonResult.success(productSpuService.addProductSpu(addSpuDto));
    }

    @PostMapping("/updateProductSpu")
    @Operation(summary = "更新商品spu")
    public CommonResult<Boolean> updateProductSpu(@Validated @RequestBody UpdateSpuDto updateSpuDto) {
        return CommonResult.success(productSpuService.updateProductSpu(updateSpuDto));
    }

    @DeleteMapping("/delProductSpu/{spuId}")
    @Operation(summary = "删除商品spu")
    public CommonResult<Boolean> delProductSpu(@PathVariable("spuId") Long spuId) {
        return CommonResult.success(productSpuService.delProductSpu(spuId));
    }

    @Operation(summary = "查询商品spu")
    @PostMapping("/queryProductSpu")
    public CommonResult<PageResult<ProductSpuVo>> queryProductSpu(@Validated @RequestBody QuerySpuDto querySpuDto) {
        return CommonResult.success(productSpuService.queryProductSpu(querySpuDto));
    }

}
