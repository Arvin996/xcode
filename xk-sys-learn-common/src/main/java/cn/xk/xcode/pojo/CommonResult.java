package cn.xk.xcode.pojo;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.GlobalErrorCodeConstants;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/5/27 15:43
 * @Version 1.0
 * @Description CommonResult
 */
@Data
@NoArgsConstructor
public class CommonResult<T> implements Serializable
{
    private T data;

    private String msg;

    private Object code;

    public static <T> CommonResult<T> error(Object code, String msg){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode){
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> CommonResult<T> success(T data){
        CommonResult<T> result = new CommonResult<>();
        result.setCode(GlobalErrorCodeConstants.SUCCESS.getCode());
        result.setMsg(GlobalErrorCodeConstants.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static boolean isSuccess(Object code){
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(serviceException.getCode(), serviceException.getMessage());
    }

    public static <T> CommonResult<T> error(ServerException serverException) {
        return error(serverException.getCode(), serverException.getMessage());
    }

    public static boolean isSuccess(CommonResult<?> result){
        return Objects.equals(result.getCode(), GlobalErrorCodeConstants.SUCCESS.getCode());
    }

}
