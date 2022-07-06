package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserOddsNowDO extends UserCategoryDO  implements Serializable {
    protected Integer oddsId;
    protected Integer playId;
    protected Double oddsA;
    protected Double oddsB;
    protected Double oddsC;
    protected Double oddsD;

}