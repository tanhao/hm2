package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class OddsRegressLimitFulloddsRQ  implements Serializable {
    private Integer loginUserId;
    private Integer userId;
    private List<Integer> lotteryIds;
    private List<UserOddsDO> oddss;
    private List<UserRegressDO> regresss;
    private List<UserRegressDO> tmrRegresss;
    private List<UserLimitDO> limits;
    private List<UserFulloddsDO> fulloddss;
    private List<UserStepDO> steps;
    private String updateIp;
    private String updateBy;

    private String json;
    private Integer errcode;
    private String errmsg;

}
