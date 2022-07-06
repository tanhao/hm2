package com.xzy.enums;

public class SmartEnums {

    public enum BetModel {
        quota, //定额
        fixed, //固定倍数
        ;
    }
    public enum WlChoice {
        win, //赢
        lose, //输
        ;
    }
    public enum BetSource {
        smart, //自动投注
        normal, //正常投注
        api, //第三方接口
        ;
    }
}
