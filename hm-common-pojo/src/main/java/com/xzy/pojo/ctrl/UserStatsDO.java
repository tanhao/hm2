package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserStatsDO   implements Serializable {
    protected Integer userId;
    protected Integer lotteryId;
    protected Integer gameNo;
    protected Integer oddsId;
    protected String market;

}