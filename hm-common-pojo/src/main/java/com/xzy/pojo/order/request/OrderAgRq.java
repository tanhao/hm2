package com.xzy.pojo.order.request;

import com.xzy.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAgRq implements Serializable {
    private Integer page=1;
    private Integer size=10;
    private Integer[] lotteryIds;
    private String startTime = DateUtil.getAccountDay().toString();
    private String endTime = DateUtil.getAccountDay().toString();
    private Integer agentId;
    private Long gameNo;
    private String username;
    private String status;
    private Integer oddsId;
    private Integer userId;
    private Integer playId;
    private Integer kindId;
    private Boolean lastMonth;
    private String day;
    private String type;

}
