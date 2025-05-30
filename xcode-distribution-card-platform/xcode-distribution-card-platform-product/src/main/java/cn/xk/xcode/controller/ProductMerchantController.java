//package cn.xk.xcode.controller;
//
//import cn.xk.xcode.entity.dto.merchant.RegisterMerchantDto;
//import cn.xk.xcode.pojo.CommonResult;
//import cn.xk.xcode.service.ProductMerchantService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @Author xuk
// * @Date 2025/5/30 13:22
// * @Version 1.0.0
// * @Description ProductMerchantController
// **/
//@RestController
//@Tag(name = "ProductMerchantController", description = "ProductMerchantController 商户信息")
//@RequestMapping("/api/product/merchant")
//public class ProductMerchantController {
//
//    @Resource
//    private ProductMerchantService productMerchantService;
//
//    @PostMapping("/register")
//    public CommonResult<Boolean> registerMerchant(@Validated @RequestBody RegisterMerchantDto registerMerchantDto) {
//        //return CommonResult.success(productMerchantService.registerMerchant(registerMerchantDto));
//    }
//
//}
