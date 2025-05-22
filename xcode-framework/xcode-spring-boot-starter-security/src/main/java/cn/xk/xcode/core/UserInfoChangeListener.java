package cn.xk.xcode.core;

import cn.xk.xcode.pojo.RefreshUserPermissionEvent;
import cn.xk.xcode.pojo.RefreshUserRoleEvent;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/5/22 17:10
 * @Version 1.0.0
 * @Description UserInfoChangeListener
 **/
@Component
public class UserInfoChangeListener {

    @EventListener
    public void onUserInfoChange(RefreshUserPermissionEvent event) {
        SaTokenLoginUtils.updateLoginUserPermission(event.getPermissions(), event.getLoginId());
    }

    @EventListener
    public void onUserRoleChange(RefreshUserRoleEvent event) {
        SaTokenLoginUtils.updateLoginUserRole(event.getPermissions(), event.getLoginId());
    }
}
