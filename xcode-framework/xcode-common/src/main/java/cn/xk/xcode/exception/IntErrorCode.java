package cn.xk.xcode.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/5/27 13:46
 * @Version 1.0
 * @Description IntErrorCode
 */
@AllArgsConstructor
public class IntErrorCode implements ErrorCode
{
    int code;

    String msg;

    @Override
    public Object getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
