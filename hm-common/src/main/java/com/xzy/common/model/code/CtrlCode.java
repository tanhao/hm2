package com.xzy.common.model.code;

import com.xzy.common.model.response.ResultCode;
import com.xzy.enums.EUserType;

import java.util.Arrays;

public enum CtrlCode implements ResultCode {
    SECURITY_CODE_ERROR(false,4001,"安保密码错误！"),
    USER_ID_NULL(false,4002,"userId不能为空！"),
    LOTTERYS_NULL(false,4003,"彩种不能为空！"),
    COMPANY_HAS_LOWER(false,4004,"公司已存在下级！"),
    AGENT1_HAS_ORDDER(false,4005,"一级代理已存在注单！"),
    COAY_DIFF_LEVEL(false,4006,"复制与被复制的人不是同级关系！"),
    COAY_DIFF_STANDARD(false,4007,"复制与被复制的人不是同类型（标准盘与非标准盘）！"),
    HAS_ORDDER(false,4008,"存在注单 暂时不能修改！"),
    BUHUO_MIN_SZ_AMT(false,4009,"设置的补货值小于实占！"),
    NO_CHANGES(false,4010,"沒有修改內容,沒法修改！"),
    ;

    Boolean success;
    int code;
    String msg;

    CtrlCode(Boolean success, int code, String msg){
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

    public static CtrlCode valueOf(int value) { //
       return  Arrays.asList(CtrlCode.class.getEnumConstants()).stream().filter(enumItem -> enumItem.code() == value).findFirst().get();
    }

}
