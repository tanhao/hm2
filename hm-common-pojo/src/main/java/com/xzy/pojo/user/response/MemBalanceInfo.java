package com.xzy.pojo.user.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemBalanceInfo {
    private String username;
    private BigDecimal balance;
    private BigDecimal winLose;
    private BigDecimal betWaiting;
    private BigDecimal credit;
    private BigDecimal betAmt;

}