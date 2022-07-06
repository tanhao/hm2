package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.UserStatsDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserStatsBO extends UserStatsDO  implements Serializable {
    private Double betAmt;
    private Double profitAmt;
}
