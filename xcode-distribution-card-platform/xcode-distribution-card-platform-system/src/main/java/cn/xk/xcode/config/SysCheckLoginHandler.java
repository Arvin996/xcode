package cn.xk.xcode.config;

import cn.xk.xcode.core.CheckLoginHandler;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.object.ObjectUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Map;

import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * @Author xuk
 * @Date 2025/5/14 9:40
 * @Version 1.0.0
 * @Description SysCheckLoginHandler
 **/
@Component
public class SysCheckLoginHandler implements CheckLoginHandler {

    @Resource
    private SystemUserService systemUserService;

    @Override
    public boolean checkLogin(String token, Map<String, Object> attributes) {
        Object loginId = StpSystemUtil.getLoginIdByToken(token);
        if (ObjectUtil.isNull(loginId)) {
            return false;
        }
        String username = loginId.toString();
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.USERNAME.eq(username));
        if (ObjectUtil.isNull(systemUserPo)) {
            return false;
        }
        attributes.put("loginId", username);
        return !CommonStatusEnum.isDisable(systemUserPo.getStatus());
    }
}
