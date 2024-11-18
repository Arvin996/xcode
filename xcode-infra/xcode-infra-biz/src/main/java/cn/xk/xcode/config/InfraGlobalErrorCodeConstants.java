package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author Administrator
 * @Date 2024/8/29 11:14
 * @Version V1.0.0
 * @Description InfraGlobalErrorCodeConstants
 */
public interface InfraGlobalErrorCodeConstants {
    ErrorCode GEN_CODE_FILE_FAILED = new IntErrorCode(200_0_500, "生成二维码错误");
}
