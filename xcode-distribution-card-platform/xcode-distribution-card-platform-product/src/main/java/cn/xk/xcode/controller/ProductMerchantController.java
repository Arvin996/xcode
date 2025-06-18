package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.dto.merchant.MerchantLoginDto;
import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
import cn.xk.xcode.entity.dto.merchant.UpdateMerchantDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/30 13:22
 * @Version 1.0.0
 * @Description ProductMerchantController
 **/
@Controller
@Tag(name = "ProductMerchantController", description = "ProductMerchantController 商户信息")
@RequestMapping("/api/product/merchant")
public class ProductMerchantController {

    @Resource
    private ProductMerchantService productMerchantService;

    @SaIgnore
    @Operation(summary = "发送找回密码邮件")
    @GetMapping("/sendFindPasswordEmail")
    @ResponseBody
    public CommonResult<Boolean> sendFindPasswordEmail(@RequestParam("email") String email) {
        return CommonResult.success(productMerchantService.sendFindPasswordEmail(email));
    }

    @SaIgnore
    @Operation(summary = "找回密码")
    @GetMapping("/findPassword")
    public ModelAndView findPassword(@RequestParam("email") String email, @RequestParam("token") String token) {
        return productMerchantService.findPassword(email, token);
    }

    @Operation(summary = "商户注册")
    @PostMapping("/register")
    @ResponseBody
    public CommonResult<Boolean> registerMerchant(@Validated @RequestBody RegisterMerchantDto registerMerchantDto) {
        return CommonResult.success(productMerchantService.registerMerchant(registerMerchantDto));
    }

    @Operation(summary = "商户基本信息更新")
    @PostMapping("/update")
    @ResponseBody
    public CommonResult<Boolean> updateMerchant(@RequestBody UpdateMerchantDto updateMerchantDto) {
        return CommonResult.success(productMerchantService.updateMerchant(updateMerchantDto));
    }

    @Operation(summary = "商户登录")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult<LoginVO> login(@RequestBody MerchantLoginDto merchantLoginDto) {
        return CommonResult.success(productMerchantService.login(merchantLoginDto));
    }

    @Operation(summary = "商户退出")
    @PostMapping("/logout")
    @ResponseBody
    public CommonResult<Boolean> logout() {
        StpMemberUtil.logout();
        return CommonResult.success(true);
    }
}
