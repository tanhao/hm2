package com.xzy.pojo.ctrl;

import java.io.Serializable;

import com.xzy.enums.EBuhuoType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KindDO implements Serializable {
    private static final long serialVersionUID = -4135539594337129998L;
    private Integer kindId;
    private String kindKey;
    private String kindName;
    private Integer groupId;
    private Integer quickSettingId;
    private EBuhuoType buhuoType;
    private Boolean isBuhuo;
    private Boolean isCljp;
    private Integer digit;
    private Integer ordering;
}