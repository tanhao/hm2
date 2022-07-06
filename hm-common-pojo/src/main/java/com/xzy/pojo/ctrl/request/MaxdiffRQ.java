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
public class MaxdiffRQ implements Serializable {
    private Integer loginUserId;
    private Integer userId;
    private List<Integer> lotteryIds;
    private List<UserMaxdiffDO> maxdiffs;

    private String json;
    private Integer errcode;
    private String errmsg;

}
