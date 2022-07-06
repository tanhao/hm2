package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.UserBuhuoDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class CljpRQ implements Serializable {
    private Integer cljpId;
    private Integer loginUserId;
    private Integer userId;
    private Integer lotteryId;
    private Integer kindId;
    private Integer model;
    private List<UserCljpDO> cljps;

    private String json;
    private Integer errcode;
    private String errmsg;

}
