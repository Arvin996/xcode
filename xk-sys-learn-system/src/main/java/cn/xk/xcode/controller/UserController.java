package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.xk.xcode.convert.UserConvert;
import cn.xk.xcode.entity.dto.user.AddUserDto;
import cn.xk.xcode.entity.dto.user.QueryUserDto;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.UserRoleService;
import cn.xk.xcode.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.entity.def.UserTableDef.USER_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.QUERY_FAILED;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.USER_ADD_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:17
 * @description
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping("/user")
public class UserController
{
    @Resource
    private UserService userService;

    @Resource
    private UserConvert userConvert;

    @Operation(summary = "根据id查询用户")
    @PostMapping("/queryById")
    public CommonResult<UserPo> queryById(@RequestBody QueryUserDto queryUserDto){
        UserPo userPo = userService.getById(queryUserDto.getId());
        if (Objects.isNull(userPo)){
           return CommonResult.error(QUERY_FAILED);
        }
        return CommonResult.success(userPo);
    }

    @Operation(summary = "查询所有用户")
    @PostMapping("/queryAll")
    public CommonResult<List<UserPo>> queryAll(){
        return CommonResult.success(userService.list());
    }

    @Operation(summary = "根据用户名查询用户")
    @PostMapping("/queryByUsername")
    public CommonResult<UserPo> queryByUsername(@RequestBody QueryUserDto queryUserDto){
        return userService.queryByUsername(queryUserDto.getUserName());
    }

    @SaCheckPermission("user:addUser")
    @Operation(summary = "新增用户")
    @PostMapping("/addUser")
    public CommonResult<Boolean> addUser(@Validated  @RequestBody AddUserDto addUserDto){
        String username = addUserDto.getUsername();
        CommonResult<UserPo> userPoCommonResult = queryByUsername(QueryUserDto.builder().userName(username).build());
        if (CommonResult.isSuccess(userPoCommonResult)){
             return CommonResult.error(USER_ADD_FAILED);
        }
        addUserDto.setPassword(SaSecureUtil.md5(addUserDto.getPassword()));
        UserPo userPo = userConvert.userAddDtoToPo(addUserDto);
        System.out.println(userPo);
        return CommonResult.success(userService.save(userPo));
    }

    @Operation(summary = "更新用户基本信息")
    @PostMapping("/updateUser")
    public CommonResult<Boolean> updateUser(@Validated  @RequestBody UpdateUserDto updateUserDto){
        return CommonResult.success(userService.updateById(userConvert.userUpdateDtoToPo(updateUserDto)));
    }

    @SaCheckPermission("user:deleteUser")
    @Operation(summary = "删除用户")
    @PostMapping("/deleteUser")
    public CommonResult<Boolean> deleteUser(@Validated  @RequestBody UpdateUserDto updateUserDto){
        return userService.deleteUser(updateUserDto);
    }

    @Operation(summary = "查询某个用户下所有的角色")
    @PostMapping("/queryRolesByUserId")
    public CommonResult<List<RolePo>> queryRolesByUserId(@Validated @RequestBody UpdateUserDto updateUserDto){
        return userService.queryRolesByUserId(updateUserDto.getId());
    }

    public static void main(String[] args) {
        System.out.printf(SaSecureUtil.md5("123456789"));
    }
}
