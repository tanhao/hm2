package com.xzy.common.model.code;

import com.xzy.common.model.response.ResultCode;

public enum MemberCode implements ResultCode {
    SYSTEM_CLOSE(false,2000,"系统维护，停止投注!"),
    LOTTERY_CLOSE(false,2001,"彩票已关闭!"),
    ODDS_NOT_EXIST(false,2002,"赔率不存在!"),
    ODDS_CLOSE(false,2003,"赔率已关闭!"),
    BET_INFO_ERROR(false,2004,"下注信息错误!"),
    GAME_NO_ERROR(false,2005,"期号错误!"),
    PAYOUT_ALREADY(false,2006,"已经开奖了!"),
    DRAWING_NOT_BET(false,2007,"己封盤!"),
    NOT_OPEN(false,2008,"还未开盘!"),
    ODDS_ERROR(false,2009,"赔率更改!"),
    MAX_BET_LIMIT(false,2010,"超过单注限额!"),
    MAX_PERIOD_LIMIT(false,2011,"超过单期限额!"),
    NOT_ENOUGH_BALANCE(false,2012,"余额不足!"),
    USER_MARKET_ERROR(false,2013,"会员盘口错误!"),
    BET_TOKEN_ERROR(false,2014,"BetToken错误!"),
    BET_MODEID_ERROR(false,2015,"下注模式与当前模式不匹配!"),
    OVER_ALLOW_WIN_AMT(false,2016,"投注错误信息128，请与上级联系"),
    MEMBER_LOCKED(false,2017,"账号已冻结,请联系上线"),
    BET_TOTAL_ERROR(false,2021,"总投注额度错误"),
    BET_LOCKED_ERROR(false,2022,"请等待上次投注完成"),
    BET_SIZE_ERROR(false,2023,"投注数量过多！"),
    BUSINESS_TIME_ERROR(false,2024,"本公司营业时间结束！"),
    BET_ERROR(false,9999,"下注失败"),
    BH_ERROR(false,8888,"补货失败"),
    BET_KLSF_ERROR(false,3001,"连码出现重复号码！");
    ;

    Boolean success;
    int code;
    String msg;

    MemberCode(Boolean success, int code, String msg){
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
