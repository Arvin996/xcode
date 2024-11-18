package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.MemberAddressClient;
import cn.xk.xcode.entity.dto.address.MemberAddressAddDto;
import cn.xk.xcode.entity.dto.address.MemberAddressBaseDto;
import cn.xk.xcode.entity.dto.address.MemberAddressUpdateDto;
import cn.xk.xcode.entity.vo.MemberAddressResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberAddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/21 19:26
 * @Version V1.0.0
 * @Description MemberAddressController
 */
@RestController
@RequestMapping("/member-address")
public class MemberAddressController implements MemberAddressClient {

    @Resource
    private MemberAddressService memberAddressService;

    @Override
    @PostMapping("/list")
    public CommonResult<List<MemberAddressResultVo>> getMemberAddressList(Long userId) {
        return CommonResult.success(memberAddressService.getMemberAddressList(userId));
    }

    @Override
    @PostMapping("/default-address")
    public CommonResult<MemberAddressResultVo> getMemberDefaultAddress(Long userId) {
        return CommonResult.success(memberAddressService.getMemberDefaultAddress(userId));
    }

    @Operation(summary = "添加地址")
    @PostMapping("/addAddress")
    public CommonResult<Boolean> addAddress(@Validated @RequestBody MemberAddressAddDto  memberAddressAddDto){
        return CommonResult.success(memberAddressService.addAddress(memberAddressAddDto));
    }

    @Operation(summary = "修改地址, 不包括默认地址")
    @PostMapping("/updateAddress")
    public CommonResult<Boolean> updateAddress(@Validated @RequestBody MemberAddressUpdateDto memberAddressUpdateDto){
        return CommonResult.success(memberAddressService.updateAddress(memberAddressUpdateDto));
    }

    @Operation(summary = "删除地址")
    @PostMapping("/deleteAddress")
    public CommonResult<Boolean> deleteAddress(@Validated @RequestBody MemberAddressBaseDto memberAddressBaseDto){
        return CommonResult.success(memberAddressService.deleteAddress(memberAddressBaseDto));
    }

    @Operation(summary = "设置默认地址")
    @PostMapping("/setDefaultAddress")
    public CommonResult<Boolean> setDefaultAddress(@Validated @RequestBody MemberAddressBaseDto memberAddressBaseDto){
        return CommonResult.success(memberAddressService.setDefaultAddress(memberAddressBaseDto));
    }
}
