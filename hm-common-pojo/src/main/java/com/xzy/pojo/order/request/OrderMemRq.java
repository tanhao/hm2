package com.xzy.pojo.order.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMemRq implements Serializable {
    private Integer page=1;
    private Integer size=10;
    private Integer lotteryId;
    private Integer agentId;
    private String status;
    private String accountDay;
    private Integer userId;

}
