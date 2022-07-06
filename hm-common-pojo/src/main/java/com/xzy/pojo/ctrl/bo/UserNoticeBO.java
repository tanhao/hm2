package com.xzy.pojo.ctrl.bo;

import com.xzy.pojo.ctrl.UserNoticeDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class UserNoticeBO extends UserNoticeDO  implements Serializable {
    private String username;
    private Boolean isMyself;
}
