package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.TakeoutUserClient;
import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.dto.user.*;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/30 10:50
 * @Version 1.0.0
 * @Description TakeoutUserController
 **/
@RestController
@RequestMapping("/takeout/user")
@Tag(name = "外卖用户用户接口")
public class TakeoutUserController implements TakeoutUserClient {

    @Resource
    private TakeoutUserService takeoutUserService;

    @GetMapping("/getTakeoutUser")
    @Override
    public CommonResult<TakeoutUserResultVo> getTakeoutUser(Long userId) {
        return CommonResult.success(takeoutUserService.getTakeoutUser(userId));
    }

    @GetMapping("/getTakeoutUserList")
    @Override
    public CommonResult<List<TakeoutUserResultVo>> getTakeUserList(Collection<Long> ids) {
        return CommonResult.success(takeoutUserService.getTakeoutUserList(ids));
    }

    @GetMapping("/getUserListByName")
    @Override
    public CommonResult<List<TakeoutUserResultVo>> getUserListByName(String name) {
        return CommonResult.success(takeoutUserService.getUserListByName(name));
    }

    @GetMapping("/getUserByMobile")
    @Override
    public CommonResult<TakeoutUserResultVo> getUserByMobile(String mobile) {
        return CommonResult.success(takeoutUserService.getUserByMobile(mobile));
    }

//    @PostMapping("/add")
//    @Operation(summary = "添加用户")
//    @SaCheckPermission("takeout:user:add")
//    public CommonResult<Boolean> addTakeoutUser(@Validated @RequestBody AddUserDto addUserDto) {
//        return CommonResult.success(takeoutUserService.addTakeoutUser(addUserDto));
//    }

    @PostMapping("/del")
    @Operation(summary = "删除用户")
    @SaCheckPermission("takeout:user:del")
    public CommonResult<Boolean> delTakeoutUser(@Validated @RequestBody UserBaseDto userBaseDto) {
        return CommonResult.success(takeoutUserService.removeById(userBaseDto.getId()));
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户基本信息")
    @SaCheckPermission("takeout:user:updateUserMsg")
    public CommonResult<Boolean> updateUserMsg(@Validated @RequestBody UpdateAddressDto updateAddressDto) {
        return CommonResult.success(takeoutUserService.updateUserMsg(updateAddressDto));
    }

    @PostMapping("/updatePassword")
    @Operation(summary = "修改用户密码")
    @SaCheckPermission("takeout:user:updatePassword")
    public CommonResult<Boolean> updatePassword(@Validated @RequestBody UpdatePasswordDto updatePasswordDto) {
        return CommonResult.success(takeoutUserService.updatePassword(updatePasswordDto));
    }

    @PostMapping("/updateRole")
    @Operation(summary = "修改用户角色")
    @SaCheckPermission("takeout:user:updateRole")
    public CommonResult<Boolean> updateRole(@Validated @RequestBody UpdateUserRoleDto updateUserRoleDto) {
        return CommonResult.success(takeoutUserService.updateRole(updateUserRoleDto));
    }

    @PostMapping("/updateStatus")
    @Operation(summary = "修改用户状态")
    @SaCheckPermission("takeout:user:updateStatus")
    public CommonResult<Boolean> updateStatus(@Validated @RequestBody UpdateUserStatusDto updateUserStatusDto) {
        return CommonResult.success(takeoutUserService.updateStatus(updateUserStatusDto));
    }

    @PostMapping("/updateMobile")
    @Operation(summary = "修改用户手机号")
    @SaCheckPermission("takeout:user:updateMobile")
    public CommonResult<Boolean> updateMobile(@Validated @RequestBody UpdateUserMobileDto updateUserMobileDto) {
        return CommonResult.success(takeoutUserService.updateMobile(updateUserMobileDto));
    }
}
