package com.xzy.result.vo;

import lombok.Data;

@Data
public class ResultInitVo {

    private Integer lotteryId;
    private String gameNo;
    private Long openTime;
    private Long actionTime;
    private Long createTime;
    private String status;
    private String accountDay;
    private String resultDay;
    private String lotteryKey;

}
