package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "hm_user_base")
public class HmUserBase  implements Serializable {
    @Id
    private Integer userId;
    private String reserveModel;
    private BigDecimal creditBalance;
    private BigDecimal useCreditBalance;
    private BigDecimal rechargeBalance;
    private BigDecimal useRechargeBalance;
    private Float commBack;
    private String marketSelect;
    private String marketOpen;
    private BigDecimal rental;
    private Integer maxWin;
    private String gainOdds;
    private String controlOdds;
    private String rentalStartDate;
    private Integer maxUser;
    private Integer maxDiff;
    private String marketValue;
    private Integer stopProfit;
    private Integer stopLoss;
}
