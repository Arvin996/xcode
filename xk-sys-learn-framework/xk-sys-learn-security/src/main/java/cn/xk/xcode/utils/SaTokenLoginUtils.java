package cn.xk.xcode.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.pojo.LoginUser;

/**
 * @Author xuk
 * @Date 2024/6/24 10:34
 * @Version 1.0
 * @Description SaTokenLoginUtils
 */
public class SaTokenLoginUtils
{
    public static final String USER_KEY = "loginUser";

    public static void doLogin(LoginUser loginUser, SaLoginModel model){
        SaLoginModel saLoginModel = ObjectUtil.defaultIfNull(model, SaLoginModel.create());
        StpUtil.login(loginUser.getUsername(), model);
        StpUtil.getTokenSession().set(USER_KEY, loginUser);
        StpUtil.getSession().updateTimeout(saLoginModel.getTimeout());
        StpUtil.getTokenSession().updateTimeout(saLoginModel.getTimeout());
    }

    public static LoginUser getLoginUser(){
        SaSession session = StpUtil.getSession();
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }

    public static LoginUser getLoginUser(String token){
        SaSession session = StpUtil.getTokenSessionByToken(token);
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }
}
