package cn.xk.xcode.handler;

import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.StpType;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.enums.CooperateType;

import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/11/21 9:58
 * @Version 1.0.0
 * @Description AddSignatureInteractiveHandler 加签处理器
 **/
@Handler
public class AddSignatureInteractiveHandler extends AbstractInteractiveHandler{

    @Override
    public boolean handle(FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(StpType.SYSTEM);
        if (loginUser == null) {
            return false;
        }
        FlowParams flowParams = new FlowParams()
                .handler(StpUtil.getLoginIdAsString())
                .permissionFlag(CollectionUtil.convertList(loginUser.getPermissions(), Function.identity()))
                .addHandlers(flowTaskInteractiveTypeVo.getAddHandlers())
                .message(CooperateType.ADD_SIGNATURE.getValue());
        return super.taskService.addSignature(flowTaskInteractiveTypeVo.getTaskId(), flowParams);
    }

    @Override
    public Integer type() {
        return CooperateType.ADD_SIGNATURE.getKey();
    }

}
