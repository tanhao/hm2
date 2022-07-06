package com.xzy.pojo.order.code;

import com.xzy.common.model.response.ResultCode;

public enum OrderCode implements ResultCode {
    SYSTEM_CLOSE(false,2000,"系统维护，停止投注!"),

    ;

    Boolean success;
    int code;
    String msg;

    OrderCode(Boolean success, int code, String msg){
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean success() {
        return this.success;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }
}