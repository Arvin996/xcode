package cn.xk.xcode.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.entity.dto.user.LoginUserDto;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.AuthService;
import cn.xk.xcode.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 16:21
 * @description
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserService userService;


    @Override
    public CommonResult<SaTokenInfo> doLogin(LoginUserDto loginUserDto) {
        CommonResult<UserPo> userPoCommonResult = userService.queryByUsername(loginUserDto.getUsername());
        if (CommonResult.isSuccess(userPoCommonResult.getCode())) {
            CommonResult.error(500, "用户名不存在");
        }
        UserPo data = userPoCommonResult.getData();
        if (!SaSecureUtil.md5(loginUserDto.getPassword()).equals(data.getPassword())) {
            return CommonResult.error(500, "密码错误");
        }
        StpUtil.login(loginUserDto.getUsername(), SaLoginConfig
                .setExtra("prev", "xk-sys-learn")
                .setExtra("username", loginUserDto.getUsername()));
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return CommonResult.success(tokenInfo);
    }
}
