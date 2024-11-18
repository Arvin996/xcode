package cn.xk.xcode.client;

import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import cn.xk.xcode.factory.TakeoutAddressClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/29 14:20
 * @Version 1.0.0
 * @Description TakeoutAddressClient
 **/
@FeignClient(value = "xk-sys-takeout-user", fallbackFactory = TakeoutAddressClientFallbackFactory.class)
@Tag(name = "外卖用户用户收货地址rpc接口")
@RequestMapping("/takeout/address")
public interface TakeoutAddressClient {

    @Operation(summary = "获取用户所有收货地址")
    @PostMapping("/list")
    CommonResult<List<TakeoutAddressResultVo>> getTakeoutAddressList(@RequestParam("userId") Long userId);

    @Operation(summary = "获取用户默认收货地址")
    @PostMapping("/default-address")
    CommonResult<TakeoutAddressResultVo> getTakeoutDefaultAddress(@RequestParam("userId") Long userId);
}
