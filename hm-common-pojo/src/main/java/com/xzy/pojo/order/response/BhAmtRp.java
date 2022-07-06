package com.xzy.pojo.order.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BhAmtRp {
    private Integer betCount;
    private Integer userId;
    private BigDecimal bhAmt;
    private BigDecimal bhWinAmt;
    private BigDecimal bhComm;


}
