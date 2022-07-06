package com.xzy.pojo.mem;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class BetRp {
    private Integer userId;
    private Integer lotteryId;
    private Long gameNo;
    private BigDecimal betTotalAmt;
    private String betInfo;
    private Integer autoNewOdds;
    private Integer errcode;
    private String betJsonArr;
    private String keys;
    private String uniqueId;
    private String betSource;
    private Date resultAccountDay;

}