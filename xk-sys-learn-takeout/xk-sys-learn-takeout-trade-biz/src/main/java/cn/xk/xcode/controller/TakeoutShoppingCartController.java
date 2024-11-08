package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.cart.AddShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ClearShoppingCartDto;
import cn.xk.xcode.entity.dto.cart.ShoppingCartBaseDto;
import cn.xk.xcode.entity.dto.cart.UpdateShoppingCartItemDto;
import cn.xk.xcode.entity.vo.cart.ShoppingCartVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/11/6 15:23
 * @Version 1.0.0
 * @Description TakeoutShoppingCartController
 **/
@RestController
@RequestMapping("/takeout/shoppingCart")
@Tag(name = "购物车接口")
public class TakeoutShoppingCartController {

    @Resource
    private TakeoutShoppingCartService takeoutShoppingCartService;

    @PostMapping("/add")
    @Operation(summary = "添加购物车")
    @SaCheckPermission
    public CommonResult<ShoppingCartVo> addShoppingCart(@Validated @RequestBody AddShoppingCartDto addShoppingCartDto){
        return CommonResult.success(takeoutShoppingCartService.addShoppingCart(addShoppingCartDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除购物车中的商品")
    @SaCheckPermission
    public CommonResult<ShoppingCartVo> delShoppingCartItem(@Validated @RequestBody ShoppingCartBaseDto shoppingCartBaseDto){
        return CommonResult.success(takeoutShoppingCartService.delShoppingCartItem(shoppingCartBaseDto));
    }

    @PostMapping("/update")
    @Operation(summary = "更新购物车中的商品信息")
    @SaCheckPermission
    public CommonResult<ShoppingCartVo> updateShoppingCartItem(@Validated @RequestBody UpdateShoppingCartItemDto updateShoppingCartItemDto){
        return CommonResult.success(takeoutShoppingCartService.updateShoppingCartItem(updateShoppingCartItemDto));
    }

    @PostMapping("/sub")
    @Operation(summary = "减少购物车中的商品数量 -1")
    @SaCheckPermission
    public CommonResult<ShoppingCartVo> subShoppingCartItem(@Validated @RequestBody ShoppingCartBaseDto shoppingCartBaseDto){
        return CommonResult.success(takeoutShoppingCartService.subShoppingCartItem(shoppingCartBaseDto));
    }

    @PostMapping("/clear")
    @Operation(summary = "清空购物车")
    @SaCheckPermission
    public CommonResult<ShoppingCartVo> clearShoppingCart(@Validated @RequestBody ClearShoppingCartDto clearShoppingCartDto){
        return CommonResult.success(takeoutShoppingCartService.clearShoppingCart(clearShoppingCartDto));
    }
}
