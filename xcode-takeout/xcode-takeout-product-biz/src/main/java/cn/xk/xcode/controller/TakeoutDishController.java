package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.TakeoutDishClient;
import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.entity.dto.dish.AddDishDto;
import cn.xk.xcode.entity.dto.dish.DishBaseDto;
import cn.xk.xcode.entity.dto.dish.QueryDishDto;
import cn.xk.xcode.entity.dto.dish.UpdateDishDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutDishService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/4 10:09
 * @Version 1.0.0
 * @Description TakeoutDishController
 **/
@RestController
@RequestMapping("/takeout/dish")
public class TakeoutDishController implements TakeoutDishClient {

    @Resource
    private TakeoutDishService takeoutDishService;

    @PostMapping("/getDishList")
    @Override
    public CommonResult<List<TakeoutDishResultVo>> getDishList() {
        return CommonResult.success(takeoutDishService.getDishList());
    }

    @PostMapping("/getDish")
    @Override
    public CommonResult<TakeoutDishResultVo> getDish(@RequestParam("id") Long id) {
        return CommonResult.success(takeoutDishService.getDish(id));
    }

    @PostMapping("/getDishes")
    @Override
    public CommonResult<List<TakeoutDishResultVo>> getDishes(@RequestParam("ids") Collection<Long> ids) {
        return CommonResult.success(takeoutDishService.getDishes(ids));
    }

    @PostMapping("/updateDishStock")
    @Override
    public CommonResult<Boolean> updateDishStock(@Validated @RequestBody UpdateStockDto updateStockDto) {
        return CommonResult.success(takeoutDishService.updateDishStock(updateStockDto));
    }

    @PostMapping("/add")
    @Operation(summary = "新增菜品")
    @SaCheckPermission("takeout:dish:add")
    public CommonResult<Boolean> addTakeoutDish(@Validated @RequestBody AddDishDto addDishDto){
        return CommonResult.success(takeoutDishService.addTakeoutDish(addDishDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除菜品")
    @SaCheckPermission("takeout:dish:del")
    public CommonResult<Boolean> delTakeoutDish(@Validated @RequestBody DishBaseDto dishBaseDto){
        return CommonResult.success(takeoutDishService.delTakeoutDish(dishBaseDto.getId()));
    }

    @PostMapping("/update")
    @Operation(summary = "更新菜品")
    @SaCheckPermission("takeout:dish:update")
    public CommonResult<Boolean> updateTakeoutDish(@Validated @RequestBody UpdateDishDto updateDishDto){
        return CommonResult.success(takeoutDishService.updateTakeoutDish(updateDishDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询菜品列表")
    @SaCheckPermission("takeout:dish:list")
    public CommonResult<PageResult<TakeoutDishResultVo>> queryDishList(@RequestBody QueryDishDto queryDishDto){
        return CommonResult.success(takeoutDishService.queryDishList(queryDishDto));
    }

}
