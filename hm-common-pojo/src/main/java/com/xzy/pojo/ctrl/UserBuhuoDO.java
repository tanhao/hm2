package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserBuhuoDO extends UserKindDO  implements Serializable {
    private Boolean isActive;
    protected Integer buhuoValue;
}