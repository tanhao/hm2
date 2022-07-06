package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString

public class UserLimitDO extends UserKindDO  implements Serializable {
    protected Integer maxBet;
    protected Integer maxPeriod;
}