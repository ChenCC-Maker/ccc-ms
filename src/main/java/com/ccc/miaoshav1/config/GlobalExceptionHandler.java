package com.ccc.miaoshav1.config;

import com.ccc.miaoshav1.exception.GlobalException;
import com.ccc.miaoshav1.result.CodeMsg;
import com.ccc.miaoshav1.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：将各类异常统一转换为 Result<CodeMsg> 返回
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(GlobalException.class)
    public Result<String> handleGlobalException(GlobalException e) {
        log.warn("业务异常: {}", e.getCodeMsg().getMsg());
        return Result.error(e.getCodeMsg());
    }

    /**
     * @RequestBody + @Valid 校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = fieldError != null ? fieldError.getDefaultMessage() : "参数校验异常";
        log.warn("参数校验异常: {}", msg);
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
    }

    /**
     * 表单参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = fieldError != null ? fieldError.getDefaultMessage() : "参数校验异常";
        log.warn("参数绑定异常: {}", msg);
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("服务端异常", e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
