package cn.xk.xcode;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/11/20 15:19
 * @Version 1.0.0
 * @Description BpmGlobalConstants
 **/
public interface BpmGlobalConstants {

    // 字典表
    String FLOW_OA_LEVE_TYPE = "leave";

    ErrorCode INTERACTIVE_HANDLER_NOT_EXISTS =  new IntErrorCode(1112_0_501, "该类型处理器不存在");
    ErrorCode LEAVE_NOT_EXISTS =  new IntErrorCode(1112_0_502, "该请假单不存在");
    ErrorCode INSTANCE_NOT_EXISTS =  new IntErrorCode(1112_0_503, "该流程实例不存在");
}
