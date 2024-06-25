package cn.xk.xcode.security.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.security.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/6/24 10:46
 * @Version 1.0
 * @Description SaTokenStpInterfaceConfig
 */
@Component
@Order(Integer.MIN_VALUE)
public class SaTokenStpInterfaceConfig implements StpInterface {

    @Override
    public List<String> getPermissionList(Object o, String s) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser();
        if (ObjectUtil.isNull(loginUser)){
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(SaTokenLoginUtils.getLoginUser().getPermissions(), Function.identity());
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser();
        if (ObjectUtil.isNull(loginUser)){
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(SaTokenLoginUtils.getLoginUser().getRoles(), Function.identity());
    }
}
