package cn.xk.xcode.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.pojo.LoginUser;

/**
 * @Author xuk
 * @Date 2024/6/24 10:34
 * @Version 1.0
 * @Description SaTokenLoginUtils
 */
public class SaTokenLoginUtils {

    public static final String USER_KEY = "loginUser";
    public static StpLogic stpLogic = null;

    public static void setStpLogic(StpLogic stpLogic){
        SaTokenLoginUtils.stpLogic = stpLogic;
    }

    public static void doLogin(LoginUser loginUser, SaLoginModel model){
        SaLoginModel saLoginModel = ObjectUtil.defaultIfNull(model, SaLoginModel.create());
        stpLogic.login(loginUser.getUsername(), model);
        stpLogic.getTokenSession().set(USER_KEY, loginUser);
        stpLogic.getSession().updateTimeout(saLoginModel.getTimeout());
        stpLogic.getTokenSession().updateTimeout(saLoginModel.getTimeout());
    }

    public static LoginUser getLoginUser(){
        SaSession session = stpLogic.getSession();
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }

    public static LoginUser getLoginUser(String token){
        SaSession session = stpLogic.getTokenSessionByToken(token);
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }
}
