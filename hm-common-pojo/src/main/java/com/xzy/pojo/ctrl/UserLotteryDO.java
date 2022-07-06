package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserLotteryDO  implements Serializable {
    protected Integer id;
    protected Integer userId;
    protected Integer lotteryId;
    protected Integer delayOpen;
    protected Integer precedeClose;
    protected Integer zmPrecedeClose;
    protected Boolean isLink;
    protected Boolean isReset;
    protected Boolean isOpenSelf;
    protected Boolean isOpenUp;
    protected Integer ordering;
}