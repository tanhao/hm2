package com.xzy.common.exception;


import com.xzy.common.model.response.ResultCode;

/**
 * 自定义异常类型
 **/
public class CustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;

    Object data;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, Object data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public Object getData() {
        return data;
    }


}
