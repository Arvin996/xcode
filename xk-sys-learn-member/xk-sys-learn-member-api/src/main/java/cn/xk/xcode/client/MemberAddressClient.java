package cn.xk.xcode.client;

import cn.xk.xcode.entity.vo.MemberAddressResultVo;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cn.xk.xcode.factory.MemberAddressFallFactory;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/8/4 20:49
 * @Version 1.0
 * @Description MemberAddressClient
 */
@FeignClient(value = "xk-sys-member", fallbackFactory = MemberAddressFallFactory.class)
@Tag(name = "用户地址rpc接口")
@RequestMapping("/member-address")
public interface MemberAddressClient
{
    @Operation(summary = "获取用户所有收货地址")
    @PostMapping("/list")
    CommonResult<List<MemberAddressResultVo>> getMemberAddressList(@RequestParam("userId") String userId);

    @Operation(summary = "获取用户默认收货地址")
    @PostMapping("/default-address")
    CommonResult<MemberAddressResultVo> getMemberDefaultAddress(@RequestParam("userId") String userId);
}
