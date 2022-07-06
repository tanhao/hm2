package com.xzy.pojo.user.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserActualPt implements Serializable {
    private Integer userId;
    private Integer lotteryId;
    private Integer ptPct;
    private Integer ptMinPct;
    private Integer ptMaxPct;
    private Integer prePct;
    private Integer preMinPct;
    private Integer preMaxPct;
    private Integer userLevel;
    private String username;
    private String userType;
    private Integer actualPct;
    private Integer actualPrePt;
    private Integer isOpenUp;
    private Integer isOpenSelf;
}
