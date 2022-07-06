package com.xzy.pojo.order.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @ClassName ReportRp
 * @Auther yzy
 * @Date 2019/9/16 10:32
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ReportRp {
    private LocalDate accountDay;
    private Integer lotteryId;
    private String lotteryKey;
    private Integer ticketNum;
    private BigDecimal betAmt;
    private BigDecimal actAmt;
    private BigDecimal winAmt;
    private BigDecimal comm;

    public ReportRp(LocalDate accountDay){
        this.accountDay = accountDay;
        this.ticketNum = 0;
        this.betAmt = new BigDecimal(0);
        this.actAmt = new BigDecimal(0);
        this.winAmt = new BigDecimal(0);
        this.comm = new BigDecimal(0);
    }
}
