package com.xzy.pojo.ctrl.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class OddsPlusMinusRQ {
    private Integer userId;
    private Integer userLevel;
    private Integer lotteryId;
    private List<Integer> oddsIds;
    private String markets;
    private Float diff;
    private String updateIp;
    private String updateBy;

    private String json;
    private Integer errcode;
    private String errmsg;
}
