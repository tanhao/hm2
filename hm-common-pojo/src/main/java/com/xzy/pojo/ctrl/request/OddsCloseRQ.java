package com.xzy.pojo.ctrl.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class OddsCloseRQ {
    private Integer userId;
    private Integer userLevel;
    private Integer lotteryId;
    private Integer oddsId;
    private Integer isClose;

    private String updateIp;
    private String updateBy;
}
