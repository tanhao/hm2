package com.xzy.pojo.result.response;

import lombok.Data;

@Data
public class ResultListRp {
    private Integer lotteryId;
    private String lotteryKey;
    private String gameNo;
    private String result;
    private Integer openTime;
    private Integer actionTime;

    public ResultListRp(Integer lotteryId, String lotteryKey, String gameNo, String result, Integer openTime, Integer actionTime) {
        this.lotteryId = lotteryId;
        this.lotteryKey = lotteryKey;
        this.gameNo = gameNo;
        this.result = result;
        this.openTime = openTime;
        this.actionTime = actionTime;
    }
}
