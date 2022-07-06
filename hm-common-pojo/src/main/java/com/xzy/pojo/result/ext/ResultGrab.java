package com.xzy.pojo.result.ext;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultGrab implements Serializable {
    private String result;
    private String https;
    private String lotteryKey;
    private String gameNo;
    private String actionTime;
    private String updateBy;
    private Boolean autoPayout;
    private Integer id;
    private Integer errcode;
    private String stats;
    private String cljp;
}
