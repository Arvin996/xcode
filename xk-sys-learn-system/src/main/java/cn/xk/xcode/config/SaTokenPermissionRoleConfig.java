package cn.xk.xcode.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.xk.xcode.controller.RoleController;
import cn.xk.xcode.controller.UserController;
import cn.xk.xcode.entity.dto.user.QueryUserDto;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.RoleService;
import cn.xk.xcode.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 15:49
 * @description
 */
@Component
public class SaTokenPermissionRoleConfig implements StpInterface {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        UserPo userPo = getUserPo(o);
        if (Objects.isNull(userPo)) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        Integer id = userPo.getId();
        CommonResult<List<RolePo>> listCommonResult = userService.queryRolesByUserId(id);
        for (RolePo datum : listCommonResult.getData()) {
            Integer roleId = datum.getId();
            CommonResult<List<ResourcePo>> commonResult = roleService.queryResourcesByRole(roleId);
            List<String> collect = commonResult.getData().stream().map(ResourcePo::getRecourseCode).collect(Collectors.toList());
            list.addAll(collect);
        }
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        UserPo userPo = getUserPo(o);
        if (Objects.isNull(userPo)) {
            return Collections.emptyList();
        }
        Integer id = userPo.getId();
        CommonResult<List<RolePo>> listCommonResult = userService.queryRolesByUserId(id);
        List<RolePo> roles = listCommonResult.getData();
        return roles.stream().map(RolePo::getName).collect(Collectors.toList());
    }

    public UserPo getUserPo(Object o) {
        CommonResult<UserPo> userPoCommonResult = userService.queryByUsername(String.valueOf(o));
        return userPoCommonResult.getData();
    }
}
