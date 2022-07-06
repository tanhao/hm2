package com.xzy.smart.code;

import com.xzy.common.model.response.ResultCode;

public enum  SmartCode implements ResultCode {
    PLAN_ADD_ERROR(false,23501,"计划添加失败！"),
    PLAN_NAME_NOT_NULL(false,23502,"计划名不能为空！"),

    ;
    /**
     * 操作是否成功
     */
    boolean success;
    /**
     * 操作代码
     */
    int code;
    /**
     * 提示信息
     */
    String message;
    SmartCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
