package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.UserBuhuoDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class ManualBuhuoRQ implements Serializable {
    private Integer userId;
    private Integer lotteryId;
    private Integer oddsId;
    private String gameNo;
    private String market;


    private Float betOdds;
    private Float betAmt;

    private String betContent  = "";

    private String uniqueId;
    private String json;
    private Integer errcode;

}
