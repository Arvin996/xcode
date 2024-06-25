package cn.xk.xcode.security.handler;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/6/24 10:10
 * @Version 1.0
 * @Description SaTokenUserLoginListenHandler
 */
@Component
@Slf4j
public class SaTokenUserLoginListenHandler implements SaTokenListener
{

    @Override
    public void doLogin(String s, Object o, String s1, SaLoginModel saLoginModel) {
        log.info("用户登录操作, userId:{}, token:{}", o, s1);
    }

    @Override
    public void doLogout(String s, Object o, String s1) {
        log.info("用户退出登录操作, userId:{}, token:{}", o, s1);
    }

    @Override
    public void doKickout(String s, Object o, String s1) {
        log.info("用户被强制踢出, userId:{}, token:{}", o, s1);
    }

    @Override
    public void doReplaced(String s, Object o, String s1) {

    }

    @Override
    public void doDisable(String s, Object o, String s1, int i, long l) {

    }

    @Override
    public void doUntieDisable(String s, Object o, String s1) {

    }

    @Override
    public void doOpenSafe(String s, String s1, String s2, long l) {

    }

    @Override
    public void doCloseSafe(String s, String s1, String s2) {

    }

    @Override
    public void doCreateSession(String s) {

    }

    @Override
    public void doLogoutSession(String s) {

    }

    @Override
    public void doRenewTimeout(String s, Object o, long l) {

    }
}
