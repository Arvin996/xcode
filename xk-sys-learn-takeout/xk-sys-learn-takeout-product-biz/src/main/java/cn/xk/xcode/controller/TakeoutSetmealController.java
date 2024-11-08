package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.TakeoutSetmealClient;
import cn.xk.xcode.entity.UpdateStockDto;
import cn.xk.xcode.entity.dto.seatmeal.AddSetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.QuerySetmealDto;
import cn.xk.xcode.entity.dto.seatmeal.SetmealBaseDto;
import cn.xk.xcode.entity.dto.seatmeal.UpdateSetmealDto;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutSetmealService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/6 9:48
 * @Version 1.0.0
 * @Description TakeoutSetmealController
 **/
@RestController
@RequestMapping("/takeout/setmeal")
public class TakeoutSetmealController implements TakeoutSetmealClient {

    @Resource
    private TakeoutSetmealService takeoutSetmealService;

    @Override
    @PostMapping("/getSetmealList")
    public CommonResult<List<TakeoutSetmealResultVo>> getSetmealList() {
        return CommonResult.success(takeoutSetmealService.getSetmealList());
    }

    @Override
    @PostMapping("/getSetmeal")
    public CommonResult<TakeoutSetmealResultVo> getSetmeal(@RequestParam("id") Long id) {
        return CommonResult.success(takeoutSetmealService.getSetmeal(id));
    }

    @PostMapping("/getSetmeals")
    @Override
    public CommonResult<List<TakeoutSetmealResultVo>> getSetmeals(@RequestParam("ids") Collection<Long> ids) {
        return CommonResult.success(takeoutSetmealService.getSetmeals(ids));
    }

    @PostMapping("/updateSetmealStock")
    @Override
    public CommonResult<Boolean> updateSetmealStock(@Validated @RequestBody UpdateStockDto updateStockDto) {
        return CommonResult.success(takeoutSetmealService.updateSetmealStock(updateStockDto));
    }

    @PostMapping("/add")
    @Operation(summary = "新增套擦")
    @SaCheckPermission("takeout:setmeal:add")
    public CommonResult<Boolean> addSetmeal(@Validated @RequestBody AddSetmealDto addSetmealDto) {
        return CommonResult.success(takeoutSetmealService.addSetmeal(addSetmealDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除套餐")
    @SaCheckPermission("takeout:setmeal:del")
    public CommonResult<Boolean> delSetmeal(@Validated @RequestBody SetmealBaseDto setmealBaseDto) {
        return CommonResult.success(takeoutSetmealService.delSetmeal(setmealBaseDto.getId()));
    }

    @PostMapping("/update")
    @Operation(summary = "修改套餐")
    @SaCheckPermission("takeout:setmeal:update")
    public CommonResult<Boolean> updateSetmeal(@Validated @RequestBody UpdateSetmealDto updateSetmealDto) {
        return CommonResult.success(takeoutSetmealService.updateSetmeal(updateSetmealDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询套餐列表")
    @SaCheckPermission("takeout:setmeal:list")
    public CommonResult<PageResult<TakeoutSetmealResultVo>> querySetmealList(@RequestBody QuerySetmealDto querySetmealDto) {
        return CommonResult.success(takeoutSetmealService.querySetmealList(querySetmealDto));
    }

}
