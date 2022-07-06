package com.xzy.pojo.ctrl;

import com.xzy.enums.EBuhuoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PlayDO implements Serializable {
    private static final long serialVersionUID = -4135539594337129998L;
    private Integer playId;
    private String playKey;
    private String playName;
    private Integer lotteryId;
    private Integer ordering;
}