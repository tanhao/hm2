package com.xzy.pojo.user.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserLotteryLimitRp implements Serializable {

        private String kindKey;
        private String marketOpen;
        private BigDecimal regress;
        private Integer minBetLimit;
        private Integer maxBetLimit;
        private Integer maxPeriodLimit;
}
