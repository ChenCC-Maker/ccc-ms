package com.ccc.miaoshav1.exception;

import com.ccc.miaoshav1.result.CodeMsg;

/**
 * 业务层抛出的全局异常，携带 CodeMsg 由 GlobalExceptionHandler 统一转换
 */
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
