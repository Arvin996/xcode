package cn.xk.xcode.exception.core;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.IllegalFormatException;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR;

/**
 * @Author xuk
 * @Date 2024/5/27 15:22
 * @Version 1.0
 * @Description ServerException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException {
    public ServerException() {
        super();
    }

    private Object code;
    /**
     * 错误提示
     */
    private String msg;

    public ServerException(Object code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }

    public ServerException(ErrorCode errorCode, Object... objs) {
        String msg = errorCode.getMessage();
        if (!ArrayUtil.isEmpty(objs)) {
            try {
                msg = StrUtil.format(errorCode.getMessage(), objs);
            } catch (IllegalFormatException e) {
                throw new ServerException(ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR);
            }
        }
        this.code = errorCode.getCode();
        this.msg = msg;
    }

    public ServerException(Object code, String msg, Object... objs) {
        if (!ArrayUtil.isEmpty(objs)) {
            try {
                msg = StrUtil.format(msg, objs);
            } catch (IllegalFormatException e) {
                throw new ServerException(ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR);
            }
        }
        this.code = code;
        this.msg = msg;
    }

}
