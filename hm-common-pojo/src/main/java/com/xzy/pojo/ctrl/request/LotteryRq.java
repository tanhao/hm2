
package com.xzy.pojo.ctrl.request;

import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.UserLotteryDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class LotteryRq extends UserLotteryDO {
    private Integer groupId;
    private String lotteryKey;
    private String lotteryName;
    private String groupName;

}