package cn.xk.xcode.client;

import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.factory.TakeoutDishClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @Author xuk
 * @Date 2024/10/31 14:43
 * @Version 1.0.0
 * @Description ProductClient
 **/
@FeignClient(value = "xk-sys-learn-takeout-product", fallbackFactory = TakeoutDishClientFallbackFactory.class)
@Tag(name = "外卖平台菜品rpc接口")
@RequestMapping("/takeout/dish")
public interface TakeoutDishClient {

    @Operation(summary = "获取所有菜品信息")
    @PostMapping("/getDishList")
    CommonResult<TakeoutDishResultVo> getDishList();

    @Operation(summary = "获取特定菜品信息")
    @PostMapping("/getDish")
    CommonResult<TakeoutDishResultVo> getDish(@RequestParam("id") Long id);

    @Operation(summary = "获取指定集合菜品信息")
    @PostMapping("/getDishes")
    CommonResult<TakeoutDishResultVo> getDishes(@RequestParam("ids") Collection<Long> id);
}
