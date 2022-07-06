package com.xzy.pojo.mem;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class BetTime implements Serializable {
    private String status;
    private Long startTime;//开始投注时间
    private Long stopTime;//关盘时间
    private Long zmStopTime;//正码关盘时间
    private Date accountDay;
}
