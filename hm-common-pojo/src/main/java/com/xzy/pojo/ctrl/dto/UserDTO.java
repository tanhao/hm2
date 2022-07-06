package com.xzy.pojo.ctrl.dto;

import com.xzy.enums.EUserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserDTO implements Serializable {
    private Integer parentId;
    private Integer userId;
    private Double regressA;
    private Double regressB;
    private Double regressC;
    private Double regressD;
    private Boolean isStandard;
    private EUserType userType;
    private Integer errcode;
    private String errmsg;

}
