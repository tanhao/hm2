package com.xzy.pojo.ctrl;

import java.io.Serializable;

import com.xzy.enums.EOpenModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LotteryDO implements Serializable {
    private static final long serialVersionUID = -5088408696255915016L;
    private Integer lotteryId;
    private String lotteryKey;
    private String lotteryName;
    private Integer groupId;
    private String shortName;
    private Integer delayOpen;
    private Integer precedeClose;
    private Integer zmPrecedeClose;
    private String codeNums;
    private Integer resultSize;
    private Integer lastResetTime;
    private Boolean isZkc;
    private EOpenModel openModel;

    private Integer ordering;

    public void JsonIgnore() {
        this.groupId = null;
        this.shortName = null;
        this.delayOpen = null;
        this.precedeClose = null;
        this.zmPrecedeClose = null;
        this.codeNums = null;
    }

}