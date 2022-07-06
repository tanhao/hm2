package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.UserOddsDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserOddsBO extends UserOddsDO  implements Serializable {
    private Double baseOddsA;
    private Double baseOddsB;
    private Double baseOddsC;
    private Double baseOddsD;

    private Double odds;
    private Double baseOdds;

    private Integer userLevel;
}
