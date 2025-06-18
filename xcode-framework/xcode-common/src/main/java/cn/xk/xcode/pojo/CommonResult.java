package cn.xk.xcode.pojo;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.GlobalErrorCodeConstants;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CommonResult<T> implements Serializable {

    private T data;

    private String msg;

    private Object code;

    //    public static <T> CommonResult<T> error(Object code, String msg){
//        CommonResult<T> result = new CommonResult<>();
//        result.setCode(code);
//        result.setMsg(msg);
//        return result;
//    }
    public static <T> CommonResult<T> error(ErrorCode errorCode, T data) {
        return error(errorCode, data, (Object) null);
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode, T data, Object... args) {
        CommonResult<T> result = new CommonResult<>();
        if (ObjectUtil.isNotNull(data)) {
            result.setData(data);
        }
        result.setCode(errorCode.getCode());
        result.setMsg(StrUtil.format(errorCode.getMessage(), args));
        return result;
    }

//    public static <T> CommonResult<T> error(ErrorCode errorCode, Object... args) {
//        return error(errorCode, null, args);
//    }

//    public static <T> CommonResult<T> error(ErrorCode errorCode, T data, Object... args) {
//        return error(errorCode.getCode(), errorCode.getMessage(), data, args);
//    }


    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode(GlobalErrorCodeConstants.SUCCESS.getCode());
        result.setMsg(GlobalErrorCodeConstants.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess(this.code);
    }

    public static boolean isSuccess(Object code) {
        return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
    }

    public static <T> CommonResult<T> error(ServiceException serviceException) {
        return error(new ErrorCode() {
            @Override
            public Object getCode() {
                return serviceException.getCode();
            }

            @Override
            public String getMessage() {
                return serviceException.getMsg();
            }
        }, null);
    }

    public static <T> CommonResult<T> error(ServerException serverException) {
        return error(new ErrorCode() {
            @Override
            public Object getCode() {
                return serverException.getCode();
            }

            @Override
            public String getMessage() {
                return serverException.getMsg();
            }
        }, null);
    }

    public static boolean isSuccess(CommonResult<?> result) {
        return isSuccess(result.getCode());
    }

}
