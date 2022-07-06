package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.UserOddsNowDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserOddsNowBO extends UserOddsNowDO implements Serializable {
    private Double odds;
    private Integer userLevel;
}
