package cn.xk.xcode.core.enums;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2025/2/14 8:51
 * @Version 1.0.0
 * @Description XcodeAiConstants
 **/
public interface XcodeAiConstants {
    ErrorCode AI_PLATFORM_NOT_FOUND = new IntErrorCode(1600_1_500, "ai platform not found");
    ErrorCode AI_PLATFORM_NOT_CONFIG_OR_NOT_SUPPORT = new IntErrorCode(1600_1_501, "ai platform not config or support");
}
