package cn.xk.xcode.constant;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/11/1 16:36
 * @Version 1.0.0
 * @Description GlobalTakeoutProductConstants
 **/
public interface GlobalTakeoutProductConstants {

    ErrorCode CATEGORY_HAS_ALREADY_EXISTS = new IntErrorCode(1100_1_500, "%s分类%s已经存在");
}
