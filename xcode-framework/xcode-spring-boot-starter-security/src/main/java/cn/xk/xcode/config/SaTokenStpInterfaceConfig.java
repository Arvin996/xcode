package cn.xk.xcode.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        LoginStpType type = LoginStpType.getType(s);
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(type);
        if (ObjectUtil.isNull(loginUser)){
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(Objects.requireNonNull(SaTokenLoginUtils.getLoginUser(type)).getPermissions(), Function.identity());
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        LoginStpType type = LoginStpType.getType(s);
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(type);
        if (ObjectUtil.isNull(loginUser)){
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(Objects.requireNonNull(SaTokenLoginUtils.getLoginUser(type)).getRoles(), Function.identity());
    }
}
