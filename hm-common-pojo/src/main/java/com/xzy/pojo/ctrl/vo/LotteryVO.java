
package com.xzy.pojo.ctrl.vo;

import java.io.Serializable;

import com.xzy.enums.EOpenModel;
import com.xzy.pojo.ctrl.UserLotteryDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LotteryVO extends UserLotteryDO implements Serializable {
    private static final long serialVersionUID = -4407647476380992623L;
    private Integer groupId;
    private String lotteryKey;
    private String lotteryName;
    private String codeNums;
    private Integer resultSize;
    private String gameNo;
    private Integer openTime;
    private Integer actionTime;
    private Boolean isZkc;
    private EOpenModel openModel;

    private String prevGameNo;
    private String prevResult;
    private String prevLmcl;
    private String prevYl;
}