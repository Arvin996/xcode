package cn.xk.xcode.entity;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/10/24 9:34
 * @Version 1.0.0
 * @Description GlobalLogConstant
 **/
public interface GlobalLogConstant {

    ErrorCode ERROR_RESOLVE_MDC_EXPRESSION = new IntErrorCode(100_0_500, "MDC表达式{}解析错误");
}
