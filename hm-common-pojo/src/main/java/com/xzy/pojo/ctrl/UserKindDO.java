package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserKindDO  implements Serializable {
    protected Integer id;
    protected Integer userId;
    protected Integer lotteryId;
    protected Integer kindId;
}