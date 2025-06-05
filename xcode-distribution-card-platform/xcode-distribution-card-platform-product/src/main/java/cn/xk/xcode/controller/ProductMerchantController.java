package cn.xk.xcode.controller;

import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.dto.merchant.MerchantLoginDto;
import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
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
 * @Date 2025/5/30 13:22
 * @Version 1.0.0
 * @Description ProductMerchantController
 **/
@RestController
@Tag(name = "ProductMerchantController", description = "ProductMerchantController 商户信息")
@RequestMapping("/api/product/merchant")
public class ProductMerchantController {

    @Resource
    private ProductMerchantService productMerchantService;

    @Operation(summary = "商户注册")
    @PostMapping("/register")
    public CommonResult<Boolean> registerMerchant(@Validated @RequestBody RegisterMerchantDto registerMerchantDto) {
        return CommonResult.success(productMerchantService.registerMerchant(registerMerchantDto));
    }

    @Operation(summary = "商户基本信息更新")
    @PostMapping("/update")
    public CommonResult<Boolean> updateMerchant(@RequestBody UpdateMerchantDto updateMerchantDto) {
        return CommonResult.success(productMerchantService.updateMerchant(updateMerchantDto));
    }

    @Operation(summary = "商户登录")
    @PostMapping("/login")
    public CommonResult<LoginVO> login(@RequestBody MerchantLoginDto merchantLoginDto) {
        return CommonResult.success(productMerchantService.login(merchantLoginDto));
    }

    @Operation(summary = "商户退出")
    @PostMapping("/logout")
    public CommonResult<Boolean> logout() {
        StpMemberUtil.logout();
        return CommonResult.success(true);
    }
}
