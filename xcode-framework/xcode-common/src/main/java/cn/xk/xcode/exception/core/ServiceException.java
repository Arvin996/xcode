package cn.xk.xcode.exception.core;


import cn.hutool.core.util.ArrayUtil;
import cn.xk.xcode.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.IllegalFormatException;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR;

/**
 * @Author xuk
 * @Date 2024/5/27 15:23
 * @Version 1.0
 * @Description ServiceException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    Object code;

    String msg;

    public ServiceException(Object code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(ErrorCode errCode) {
        this.code = errCode.getCode();
        this.msg = errCode.getMessage();
    }

    public ServiceException(ErrorCode errorCode, Object... objs) {
        String msg = errorCode.getMessage();
        if (!ArrayUtil.isEmpty(objs)){
            try {
                msg = String.format(errorCode.getMessage(), objs);
            } catch (IllegalFormatException e) {
                throw new ServiceException(ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR);
            }
        }
        this.code = errorCode.getCode();
        this.msg = msg;
    }

    public ServiceException(Object code, String msg , Object... objs) {
        if (!ArrayUtil.isEmpty(objs)){
            try {
                msg = String.format(msg, objs);
            } catch (IllegalFormatException e) {
                throw new ServiceException(ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR);
            }
        }
        this.code = code;
        this.msg = msg;
    }
}
