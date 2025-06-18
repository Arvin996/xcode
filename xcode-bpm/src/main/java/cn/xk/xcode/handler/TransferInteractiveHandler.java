package cn.xk.xcode.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.enums.CooperateType;

import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/11/21 10:24
 * @Version 1.0.0
 * @Description TransferInteractiveHandler
 **/
@Handler
public class TransferInteractiveHandler extends AbstractInteractiveHandler{
    @Override
    public boolean handle(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(LoginStpType.SYSTEM);
        if (loginUser == null) {
            return false;
        }
        FlowParams flowParams = new FlowParams()
                .handler(StpUtil.getLoginIdAsString())
                .permissionFlag(CollectionUtil.convertList(loginUser.getPermissions(), Function.identity()))
                .addHandlers(flowTaskInteractiveTypeVo.getAddHandlers())
                .message(CooperateType.TRANSFER.getValue());
        return super.taskService.transfer(flowTaskInteractiveTypeVo.getTaskId(), flowParams);
    }

    @Override
    public Integer type() {
        return CooperateType.TRANSFER.getKey();
    }
}
