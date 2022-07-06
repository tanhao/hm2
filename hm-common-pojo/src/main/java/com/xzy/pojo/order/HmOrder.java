package com.xzy.pojo.order;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name="hm_order")
public class HmOrder  implements Serializable {
    private String uniqueId;
    @Id
    private String orderId;
    private Date accountDay;
    private Integer betUserId;
    private Integer userId;
    private Integer lotteryId;
    private String gameNo;
    private String market;
    private Integer oddsId;
    private Float odds;
    private String oddsKey;
    private BigDecimal betAmt;
    private Long betTime;
    private String betContent;
    private String status;
    private BigDecimal winAmt;
    private String ticketStatus;
    private Date dividendTime;
    private Date updated;
    private String updateBy;
    private Float commPct;
    private String ids;
    private String keyName;
    private String betType;
    private String voidReason;
    private Boolean manual;
    @Transient
    private String username;



}
