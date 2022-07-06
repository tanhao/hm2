package com.xzy.pojo.logs.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class HmLogsRq implements Serializable {
    private int page=1;
    private int size=10;
    private Integer userId;
    private String startTime;
    private String endTime;
}
