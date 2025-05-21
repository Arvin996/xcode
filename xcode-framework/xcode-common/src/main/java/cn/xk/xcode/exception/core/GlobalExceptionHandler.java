package cn.xk.xcode.exception.core;

import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.PARAMETER_VALIDATION_FAIL;

/**
 * @Author xuk
 * @Date 2024/8/6 15:02
 * @Version 1.0
 * @Description GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResult<?> handlerException(Exception e) {
        log.error("发生异常:{}", e.getMessage());
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandler(ServerException.class)
    public CommonResult<?> handlerServerException(ServerException e) {
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public CommonResult<?> handlerServiceException(ServiceException e) {
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CommonResult.error(PARAMETER_VALIDATION_FAIL.getCode(),
                PARAMETER_VALIDATION_FAIL.getMessage() + ":" + e.getMessage());
    }
}
