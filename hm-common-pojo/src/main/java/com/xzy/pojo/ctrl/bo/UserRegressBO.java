package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.UserRegressDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserRegressBO extends UserRegressDO  implements Serializable {
    private Double baseRegressA;
    private Double baseRegressB;
    private Double baseRegressC;
    private Double baseRegressD;

    private Integer maxBet;
    private Integer maxPeriod;
    private Integer baseMaxBet;
    private Integer baseMaxPeriod;


    private Double tmrRegressA;
    private Double tmrRegressB;
    private Double tmrRegressC;
    private Double tmrRegressD;
}
