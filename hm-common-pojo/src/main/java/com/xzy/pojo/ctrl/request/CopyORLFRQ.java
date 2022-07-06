package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.UserFulloddsDO;
import com.xzy.pojo.ctrl.UserLimitDO;
import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.UserRegressDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class CopyORLFRQ implements Serializable {
    private Integer toUserId;
    private Integer fromUserId;
    private Integer errcode;
    private String errmsg;

}
