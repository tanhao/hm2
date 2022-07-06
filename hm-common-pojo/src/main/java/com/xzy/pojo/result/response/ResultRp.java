package com.xzy.pojo.result.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName ResultRp
 * @Auther yzy
 * @Date 2019/6/5 10:07
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class ResultRp {
    private Integer id;
    private Integer lotteryId;
    private String gameNo;
    private Long openTime;
    private Long actionTime;
    private String result;
    private String prevGameNo;
    private String stats;
}
