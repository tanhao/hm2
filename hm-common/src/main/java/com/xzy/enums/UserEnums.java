package com.xzy.enums;

//用户枚举类
public class UserEnums {

    //用户状态
    public enum Status {
        OPEN, SUSPEND, CLOSE,
        ;
    }

    //用户类型 代理/会员
    public enum Utype {
        AGENT, MEMBER;
    }

    //登录方式
    public enum LoginMode {
        PC, H5, APP, APK, WECHAT,
        ;
    }

    //登录类型
    public enum LoginType {
        MEMBER, AGENT, ADMIN;
    }

    //市场盘口
    public enum Market {
        A, B, C, D;
    }

    //占成模式 对会员占成 /对代理占成
    public enum PtModel {
        MEMBER_PT, COMPANY_PT,
        ;
    }

    //额度模式 现金/信用
    public enum ReserveModel {
        RECHARGE, CREDIT, OMNIBUS;
    }

    public enum AccessOperation {
        DEPOSIT, WITHDRAWAL, RECYCLE;
    }

    public enum LogType {
        LOGIN, LOGOUT,
    }

    public enum JumpType{
        SZ,XZ
    }

}
