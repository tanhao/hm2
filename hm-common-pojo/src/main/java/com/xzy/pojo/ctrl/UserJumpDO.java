package com.xzy.pojo.ctrl;

import com.xzy.enums.EJumpType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserJumpDO extends UserKindDO implements Serializable {
    protected Integer jumpAmt;
    protected Double jumpValue;
    protected EJumpType jumpType;
    protected Double jumpMax;
}