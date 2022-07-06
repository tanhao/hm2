package com.xzy.pojo.order.request;

import com.xzy.utils.DateUtil;
import lombok.Data;

@Data
public class WinLoseRq {
    private Integer userId;
    private String startTime = DateUtil.getAccountDay().toString();
    private String endTime = DateUtil.getAccountDay().toString();
    private String status;
    private String type;
    private String username;
    private String gameNo;
    private Integer[] lotteryIds;
    private Integer playId;
    private Integer kindId;
    private Integer lotteryId;
    private Integer parentId;
    private String sort;
}