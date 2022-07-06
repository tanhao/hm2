package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserOddsCljpDO;
import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.UserOddsJumpDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserOddsCljpBO extends UserOddsCljpDO implements Serializable {
    private Integer userLevel;
}
