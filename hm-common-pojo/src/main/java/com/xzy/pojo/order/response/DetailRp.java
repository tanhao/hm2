package com.xzy.pojo.order.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailRp {
    private String orderId;
    private Integer userId;
    private String username;
    private Integer oddsAgentId;
    private Integer lotteryId;
    private String gameNo;
    private String market;
    private Integer userOddsId;
    private BigDecimal odds;
    private String oddsKey;
    private BigDecimal betAmt;
    private Long betTime;
    private String betContent;
    private String status;
    private BigDecimal winAmt;
    private String ticketStatus;
    private Long dividendTime;
    private String result;
    private Float commPct;
    private String categoryKey;
    private Integer betStatus;
    private BigDecimal bhAmt;
    private BigDecimal bhWinAmt;
    private BigDecimal bhComm;
    private String betType;

}