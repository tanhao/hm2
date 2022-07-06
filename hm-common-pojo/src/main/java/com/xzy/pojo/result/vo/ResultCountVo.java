package com.xzy.pojo.result.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultCountVo implements Serializable {
    private Integer lotteryId;
    private String lotteryKey;
    private Integer countTotal;
    private Integer countNull;
    private Integer countWaiting;

}
