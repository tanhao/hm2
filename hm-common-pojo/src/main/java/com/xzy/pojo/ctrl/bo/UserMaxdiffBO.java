package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserMaxdiffDO;
import com.xzy.pojo.ctrl.UserOddsDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserMaxdiffBO extends UserMaxdiffDO implements Serializable {
    private Double minMaxdiff;
    private Double maxMaxdiff;
}
