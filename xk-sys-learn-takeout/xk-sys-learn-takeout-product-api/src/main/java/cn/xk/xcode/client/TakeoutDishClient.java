package cn.xk.xcode.client;

import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.factory.TakeoutDishClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

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
    CommonResult<List<TakeoutDishResultVo>> getDishList();

    @Operation(summary = "获取特定菜品信息")
    @PostMapping("/getDish")
    CommonResult<TakeoutDishResultVo> getDish(@RequestParam("id") Long id);

    @Operation(summary = "获取指定集合菜品信息")
    @PostMapping("/getDishes")
    CommonResult<List<TakeoutDishResultVo>> getDishes(@RequestParam("ids") Collection<Long> ids);

    @Operation(summary = "更新菜品库存")
    @PostMapping("/updateDishStock")
    CommonResult<Boolean> updateDishStock(@Validated @RequestBody UpdateStockDto updateStockDto);
}
