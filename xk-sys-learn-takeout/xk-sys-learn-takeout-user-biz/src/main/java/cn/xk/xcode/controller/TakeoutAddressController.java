package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.TakeoutAddressClient;
import cn.xk.xcode.entity.dto.address.AddAddressDto;
import cn.xk.xcode.entity.dto.address.AddressBaseDto;
import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutAddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/30 9:35
 * @Version 1.0.0
 * @Description TakeoutAddressController
 **/
@RestController
@RequestMapping("/takeout/address")
public class TakeoutAddressController implements TakeoutAddressClient {

    @Resource
    private TakeoutAddressService takeoutAddressService;

    @Override
    @PostMapping("/list")
    public CommonResult<List<TakeoutAddressResultVo>> getTakeoutAddressList(@RequestParam("userId") Long userId) {
        return CommonResult.success(takeoutAddressService.getTakeoutAddressList(userId));
    }

    @PostMapping("/default-address")
    @Override
    public CommonResult<TakeoutAddressResultVo> getTakeoutDefaultAddress(@RequestParam("userId") Long userId) {
        return CommonResult.success(takeoutAddressService.getTakeoutDefaultAddress(userId));
    }

    @PostMapping("/add")
    @Operation(summary = "添加收货地址")
    @SaCheckPermission("takeout:address:add")
    public CommonResult<Boolean> addTakeoutAddress(@Validated @RequestBody AddAddressDto addAddressDto){
        return CommonResult.success(takeoutAddressService.addTakeoutAddress(addAddressDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除收货地址")
    @SaCheckPermission("takeout:address:del")
    public CommonResult<Boolean> delTakeoutAddress(@Validated @RequestBody AddressBaseDto addressBaseDto){
        return CommonResult.success(takeoutAddressService.removeById(addressBaseDto.getId()));
    }

    @PostMapping("/update")
    @Operation(summary = "修改收货地址")
    @SaCheckPermission("takeout:address:update")
    public CommonResult<Boolean> updateTakeoutAddress(@Validated @RequestBody UpdateAddressDto updateAddressDto){
        return CommonResult.success(takeoutAddressService.updateTakeoutAddress(updateAddressDto));
    }

    @PostMapping("/setDefault")
    @Operation(summary = "设置默认收货地址")
    @SaCheckPermission("takeout:address:setDefault")
    public CommonResult<Boolean> setDefaultTakeoutAddress(@Validated @RequestBody AddressBaseDto addressBaseDto){
        return CommonResult.success(takeoutAddressService.setDefaultTakeoutAddress(addressBaseDto));
    }
}
