package com.xzy.pojo.ctrl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class QuickSettingDO implements Serializable {
    private static final long serialVersionUID = 7412430590616749272L;
    private Integer quickSettingId;
    private String quickSettingKey;
    private String quickSettingName;
    private Integer ordering;

    private Integer regressA=0;
    private Integer regressB=0;
    private Integer regressC=0;
    private Integer regressD=0;
    private Integer baseRegressA=0;
    private Integer baseRegressB=0;
    private Integer baseRegressC=0;
    private Integer baseRegressD=0;
    private Integer maxBet=0;
    private Integer maxPeriod=0;
    private Integer baseMaxBet=0;
    private Integer baseMaxPeriod=0;

}