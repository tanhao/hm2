package com.xzy.pojo.result.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultRq implements Serializable {
    private String accountDay;
    private Integer lotteryId;
    private Long gameNo;
    private Integer page;
    private Integer size;
    
}
