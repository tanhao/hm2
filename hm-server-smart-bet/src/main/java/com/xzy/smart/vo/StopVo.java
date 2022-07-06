package com.xzy.smart.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StopVo {
    private Integer userId;
    private BigDecimal stopLoss;
    private BigDecimal stopProfit;
    private BigDecimal betWinAmt;

}
