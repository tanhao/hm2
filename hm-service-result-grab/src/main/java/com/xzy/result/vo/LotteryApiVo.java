package com.xzy.result.vo;

import lombok.Data;

@Data
public class LotteryApiVo {


    private Integer gameCount;
    private Integer nextRound;
    private String gameNo;
    private String actionTime;
    private String result;
    private String nextGameNo;
    private String nextActionTime;

    private String lotteryKey;
    private Integer groupId;
    private String startTime;
    private Integer invlSecond;
    private Integer totalRound;

}
