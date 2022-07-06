package com.xzy.result.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ZkcVo implements Serializable {
    public String lotteryKey;
    public String lotteryId;
    public String gameNo;
    public String resultStr;
    public BigDecimal winAmt;
    public BigDecimal commAmt;


}
