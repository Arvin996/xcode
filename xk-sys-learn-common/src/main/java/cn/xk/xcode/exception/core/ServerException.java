package cn.xk.xcode.exception.core;

import cn.xk.xcode.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2024/5/27 15:22
 * @Version 1.0
 * @Description ServerException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException
{
    public ServerException(){
        super();
    }

    private Object code;
    /**
     * 错误提示
     */
    private String message;

    public ServerException(Object code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServerException(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
