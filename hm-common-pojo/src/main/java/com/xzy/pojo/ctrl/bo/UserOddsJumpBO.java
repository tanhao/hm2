package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.UserOddsJumpDO;
import com.xzy.pojo.ctrl.UserOddsNowDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserOddsJumpBO extends UserOddsJumpDO implements Serializable {
    private Integer userLevel;
}
