package com.ccc.miaoshav1.result;

import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        Result<T> result = new Result<>();
        result.setCode(codeMsg.getCode());
        result.setMsg(codeMsg.getMsg());
        return result;
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
