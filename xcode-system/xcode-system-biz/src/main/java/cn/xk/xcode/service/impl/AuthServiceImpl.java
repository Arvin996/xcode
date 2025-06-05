package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.handler.LoginHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.INVALID_GRANT_TYPE;

/**
 * @Author xuk
 * @Date 2024/6/24 14:16
 * @Version 1.0
 * @Description AuthServiceImpl
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private LoginHandlerHolder loginHandlerHolder;

    @Override
    public CommonResult<LoginVO> doLogin(LoginInfoDto loginUserDto) {
        AbstractLoginHandler handler = loginHandlerHolder.routeLoginHandler(loginUserDto.getGrantType());
        if (ObjectUtil.isNull(handler)){
            throw new ServerException(INVALID_GRANT_TYPE);
        }
        return CommonResult.success(handler.Login(loginUserDto));
    }

    @Override
    public CommonResult<Boolean> logout() {
        // 注意执行顺序，最后再执行logout
        StpUtil.getTokenSession().logout(); // 清除缓存session
        StpUtil.logout();   // 退出登录
        return CommonResult.success(true);
    }

    @Override
    public CommonResult<Boolean> kickout(LoginInfoDto loginInfoDto) {
        StpUtil.getTokenSession().logout(); // 清除缓存session
        StpUtil.kickout(loginInfoDto.getUsername(), loginInfoDto.getLoginDevType());
        return CommonResult.success(true);
    }
}
