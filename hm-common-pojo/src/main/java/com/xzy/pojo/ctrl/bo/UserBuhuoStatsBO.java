package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserOddsDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserBuhuoStatsBO  implements Serializable {
   private Integer lotteryId;
   private Integer kindId;
   private Double amt;
}
