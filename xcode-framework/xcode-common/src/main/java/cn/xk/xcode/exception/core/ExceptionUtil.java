package cn.xk.xcode.exception.core;

import cn.xk.xcode.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuk
 * @Date 2024/5/27 15:37
 * @Version 1.0
 * @Description ExceptionUtil
 */
@Slf4j
public class ExceptionUtil {
    public static void castServiceException0(Object code, String message) {
        throw new ServiceException(code, message);
    }

    public static void castServiceException0(Object code, String message, Object... objs) {
        throw new ServiceException(code, message, objs);
    }

    public static void castServiceException(ErrorCode errorCode) {
        castServiceException0(errorCode.getCode(), errorCode.getMessage());
    }

    public static void castServerException0(Object code, String message) {
        throw new ServerException(code, message);
    }

    public static void castServerException0(Object code, String message, Object... objs) {
        throw new ServerException(code, message, objs);
    }

    public static void castServerException(ErrorCode errorCode) {
        castServerException0(errorCode.getCode(), errorCode.getMessage());
    }

    public static void castServiceException(ErrorCode errorCode, Object... objs) {
        castServiceException0(errorCode.getCode(), errorCode.getMessage(), objs);
    }

    public static void castServerException(ErrorCode errorCode, Object... objs) {
        castServerException0(errorCode.getCode(), errorCode.getMessage(), objs);
    }

}
