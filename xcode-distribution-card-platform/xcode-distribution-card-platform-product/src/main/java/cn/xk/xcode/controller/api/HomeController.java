package cn.xk.xcode.controller.api;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.xk.xcode.entity.vo.api.ProductIndexVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.api.HomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/6/6 16:10
 * @Version 1.0.0
 * @Description HomeController
 **/
@RestController
@RequestMapping("/home")
@Tag(name = "HomeController", description = "HomeController 首页精选")
public class HomeController {

    @Resource
    private HomeService homeService;

    @SaIgnore
    @GetMapping("/index")
    public CommonResult<ProductIndexVo> index(@RequestParam(name = "storeId") Long storeId) {
        return CommonResult.success(homeService.getProductIndex());
    }
}
