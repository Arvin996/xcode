package cn.xk.xcode.client;

import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.factory.TakeoutSetmealClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
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
 * @Date 2024/11/1 11:06
 * @Version 1.0.0
 * @Description TakeoutSetmealClient
 **/
@FeignClient(value = "xk-sys-learn-takeout-product", fallbackFactory = TakeoutSetmealClientFallbackFactory.class)
@Tag(name = "外卖平台套餐rpc接口")
@RequestMapping("/takeout/setmeal")
public interface TakeoutSetmealClient {

    @Operation(summary = "获取所有套餐信息")
    @PostMapping("/getSetmealList")
    CommonResult<List<TakeoutSetmealResultVo>> getSetmealList();

    @Operation(summary = "获取特定菜品信息")
    @PostMapping("/getSetmeal")
    CommonResult<TakeoutSetmealResultVo> getSetmeal(@RequestParam("id") Long id);

    @Operation(summary = "获取指定集合菜品信息")
    @PostMapping("/getSetmeals")
    CommonResult<List<TakeoutSetmealResultVo>> getSetmeals(@RequestParam("ids") Collection<Long> ids);

    @Operation(summary = "更新套餐库存")
    @PostMapping("/updateSetmealStock")
    CommonResult<Boolean> updateSetmealStock(@Validated @RequestBody UpdateStockDto updateStockDto);
}
