package com.xzy.pojo.user.response;

import com.xzy.pojo.user.HmUserPrePt;
import lombok.Data;

@Data
public class UserPrePtRp  extends HmUserPrePt {
    private String lotteryName;
    private String groupName;
    private String userName;
    private Integer userLevel;
    private String userType;
}
