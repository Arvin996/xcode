package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.flavor.AddFlavorDto;
import cn.xk.xcode.entity.dto.flavor.FlavorBaseDto;
import cn.xk.xcode.entity.dto.flavor.QueryFlavorDto;
import cn.xk.xcode.entity.dto.flavor.UpdateFlavorDto;
import cn.xk.xcode.entity.vo.flavor.FlavorResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutFlavorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/4 9:03
 * @Version 1.0.0
 * @Description TakeoutFlavorController
 **/
@RestController
@RequestMapping("/takeout/flavor")
public class TakeoutFlavorController {

    @Resource
    private TakeoutFlavorService takeoutFlavorService;

    @PostMapping("/add")
    @Operation(summary = "新增口味")
    @SaCheckPermission("takeout:flavor:add")
    public CommonResult<Boolean> addTakeoutFlavor(@Validated @RequestBody AddFlavorDto addFlavorDto) {
        return CommonResult.success(takeoutFlavorService.addTakeoutFlavor(addFlavorDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除口味")
    @SaCheckPermission("takeout:flavor:del")
    public CommonResult<Boolean> delTakeoutFlavor(@Validated @RequestBody FlavorBaseDto flavorBaseDto) {
        return CommonResult.success(takeoutFlavorService.delTakeoutFlavor(flavorBaseDto));
    }

    @PostMapping("/update")
    @Operation(summary = "更新口味")
    @SaCheckPermission("takeout:flavor:update")
    public CommonResult<Boolean> updateTakeoutFlavor(@Validated @RequestBody UpdateFlavorDto updateFlavorDto) {
        return CommonResult.success(takeoutFlavorService.updateTakeoutFlavor(updateFlavorDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询口味")
    @SaCheckPermission("takeout:flavor:list")
    public CommonResult<List<FlavorResultVo>> listTakeoutFlavor(@Validated @RequestBody QueryFlavorDto queryFlavorDto) {
        return CommonResult.success(takeoutFlavorService.listTakeoutFlavor(queryFlavorDto));
    }
}