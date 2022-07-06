package com.xzy.common.model.code;

import com.xzy.common.model.response.ResultCode;
import lombok.ToString;

/**
 * 通用编码返回
 */
@ToString
public enum CommonCode implements ResultCode {
    /**
     * 成功方法
     */
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,11111,"操作失败！"),
    UNAUTHENTICATED(false,10001,"账号已登出！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),
    INVALID_PARAM(false,10003,"无效参数！"),
    REPEAT_SUBMIT(false,10004,"重复请求！"),
    ACCOUNT_CLOSED(false,10005,"账号已关闭！"),
    START_TIME_CLOSED(false,10006,"公司开始营业时间不能小于 07:00！"),
    END_TIME_CLOSED(false,10006,"公司闭关营业时间不能大于 06:00！"),
    SUPERIOR_UPDATE(false,10007,"上级已修改该状态 请刷新页面！"),
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
    CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }
    @Override
    public String message() {
        return message;
    }


}
