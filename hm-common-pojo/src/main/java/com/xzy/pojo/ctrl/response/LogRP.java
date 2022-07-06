package com.xzy.pojo.ctrl.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class LogRP implements Serializable {
    private Integer lotteryId;
    private String lotteryName;

    private Integer kindId;
    private String kindName;

    private String detail;

    private String updateIp;
    private String updateAddr;
    private String updateBy;
    private String updateTime;

    private Integer categoryId;
    private String categoryName;

    private Integer oddsId;
    private String oddsName;

    private Integer playId;
    private String playName;

    private String gameNo;
    private String userId;
    private String username;

    private String type;

}
