package com.xzy.common.model.code;

public enum HmResultCode implements com.xzy.common.model.response.ResultCode {
    NOT_FOUND_RESULT(false,3000,"未获取到赛果!"),
    RESULT_ERROR(false,3001,"赛果数据错误!"),
    ACTION_SUCCESS(false,3002,"开奖成功!"),
    NO_UPDATE_RESULT(false,204,"未修改赛果！"),
    NO_BH_UNDONE(false,101,"补货数据未完成！"),
    NO_RESULT(false,203,"赛果未找到！"),
    NO_RESULT_NULL(false,203,"赛果不能为空！"),
    NO_NOTICE_TYPE(false,20101,"添加的通知类型不能为空！"),
    NO_NOTICE_CONTENT(false,20101,"添加的通知内容不能为空！"),
    NO_RESULT_DATE(false,20102,"重派日期不在当前一周之内！"),
    ;

    Boolean success;
    int code;
    String msg;

    HmResultCode(Boolean success, int code, String msg){
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
