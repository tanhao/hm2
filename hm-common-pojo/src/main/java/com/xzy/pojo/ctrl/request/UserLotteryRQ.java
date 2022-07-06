package com.xzy.pojo.ctrl.request;

import com.xzy.pojo.ctrl.LotteryDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class UserLotteryRQ  implements Serializable {
    //update status
    private Integer lotteryId;
    private Integer groupId;
    private Boolean isReset;       //是否重置赔率
    private Boolean isLink;        //是否开启联动
    private Boolean isOpen;        //是否游戏赋权

    //update time
    private Integer delayOpen;
    private Integer precedeClose;
    private Integer zmPrecedeClose;

    private String openModel;

    private List<LotteryDO> lotterys;


}
