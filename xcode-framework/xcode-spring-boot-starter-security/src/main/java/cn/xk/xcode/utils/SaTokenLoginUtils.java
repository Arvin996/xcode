package cn.xk.xcode.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.StpType;

import java.util.Objects;

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

    public static LoginUser getLoginUser(StpType stpType){
        SaSession session;
        if (Objects.equals(stpType, StpType.SYSTEM)){
            session = StpSystemUtil.getSession();
        }else {
            session = StpMemberUtil.getSession();
        }
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }

    public static LoginUser getLoginUser(StpType stpType, String token){
        SaSession session;
        if (Objects.equals(stpType, StpType.SYSTEM)){
            session = StpSystemUtil.getTokenSessionByToken(token);
        }else {
            session = StpMemberUtil.getTokenSessionByToken(token);
        }
        return ObjectUtil.isNull(session) ? null : (LoginUser) session.get(USER_KEY);
    }
}
