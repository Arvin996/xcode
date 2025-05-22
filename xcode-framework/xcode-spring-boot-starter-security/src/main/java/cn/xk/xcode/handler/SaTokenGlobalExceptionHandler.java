package cn.xk.xcode.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.xk.xcode.exception.GlobalErrorCodeConstants;
import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author xuk
 * @Date 2024/6/24 10:04
 * @Version 1.0
 * @Description SaTokenGlobalExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class SaTokenGlobalExceptionHandler
{
    @ExceptionHandler(NotLoginException.class)
    public CommonResult<String> notLoginExceptionHandler(NotLoginException e)
    {
        log.error(e.getMessage());
        String message;
        if (e.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        } else if (e.getType().equals(NotLoginException.INVALID_TOKEN) || e.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "您的登录信息已过期，请重新登录以继续访问。";
            // TODO: websocket close
            // sendWsClose();
        } else if (e.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        } else if (e.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        } else if (e.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        } else if (e.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        } else {
            message = "当前会话未登录";
        }
        return CommonResult.error(GlobalErrorCodeConstants.INVALID_TOKEN.getCode(), message, null);
    }

    @ExceptionHandler(NotPermissionException.class)
    public CommonResult<String> handlerNotPermissionException(NotPermissionException e) {
        log.error(e.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN);
    }


}
