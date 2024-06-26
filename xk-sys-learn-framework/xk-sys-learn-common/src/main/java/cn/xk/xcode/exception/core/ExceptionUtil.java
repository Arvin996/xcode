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
public class ExceptionUtil
{
    public static ServiceException castServiceException0(Object code, String message){
        return new ServiceException(code,message);
    }

    public static ServiceException castServiceException(ErrorCode errorCode){
        return castServiceException0(errorCode.getCode(),errorCode.getMessage());
    }

    public static ServerException castServerException0(Object code, String message){
        return new ServerException(code, message);
    }

    public static ServerException castServerException(ErrorCode errorCode){
        return castServerException0(errorCode.getCode(),errorCode.getMessage());
    }

}
