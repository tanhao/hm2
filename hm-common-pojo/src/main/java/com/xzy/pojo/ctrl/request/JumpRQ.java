package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.UserBuhuoDO;
import com.xzy.pojo.ctrl.UserJumpDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class JumpRQ implements Serializable {
    private Integer loginUserId;
    private Integer userId;
    private String jumpType;
    private List<Integer> lotteryIds;
    private List<UserJumpDO> jumps;

    private String json;
    private Integer errcode;
    private String errmsg;

}
