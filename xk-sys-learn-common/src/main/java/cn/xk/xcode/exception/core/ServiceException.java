package cn.xk.xcode.exception.core;


import cn.xk.xcode.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2024/5/27 15:23
 * @Version 1.0
 * @Description ServiceException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException
{
    public ServiceException()
    {
        super();
    }

    Object code;

    String msg;

    public ServiceException(Object code, String msg)
    {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg)
    {
        super(msg);
        this.msg = msg;
    }

   public ServiceException(ErrorCode errCode){
        this.code = errCode.getCode();
        this.msg = errCode.getMessage();
   }
}
