package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.store.AddStoreDto;
import cn.xk.xcode.entity.dto.store.QueryStoreDto;
import cn.xk.xcode.entity.dto.store.UpdateStoreDto;
import cn.xk.xcode.entity.vo.store.ProductStoreVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.ProductStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/3 9:44
 * @Version 1.0.0
 * @Description ProductStoreController
 **/
@RestController
@Tag(name = "ProductStoreController", description = "ProductStoreController 店铺controller")
@RequestMapping("/api/product/store")
public class ProductStoreController {

    @Resource
    private ProductStoreService productStoreService;

    @PostMapping("/addProductStore")
    @Operation(summary = "添加店铺")
    public CommonResult<Boolean> addProductStore(@Validated @RequestBody AddStoreDto addStoreDto) {
        return CommonResult.success(productStoreService.addProductStore(addStoreDto));
    }

    @PostMapping("/updateProductStore")
    @Operation(summary = "更新店铺")
    public CommonResult<Boolean> updateProductStore(@Validated @RequestBody UpdateStoreDto updateStoreDto) {
        return CommonResult.success(productStoreService.updateProductStore(updateStoreDto));
    }

    @DeleteMapping("/delProductStore/{storeId}")
    @Operation(summary = "删除店铺")
    public CommonResult<Boolean> delProductStore(@PathVariable("storeId") Long storeId) {
        return CommonResult.success(productStoreService.delProductStore(storeId));
    }

    @Operation(summary = "查询店铺")
    @PostMapping("/queryProductStores")
    public CommonResult<List<ProductStoreVo>> queryProductStores(@RequestBody QueryStoreDto queryStoreDto) {
        return CommonResult.success(productStoreService.queryProductStores(queryStoreDto));
    }

}
