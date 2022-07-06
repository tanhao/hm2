package com.xzy.pojo.user.request;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLogRq implements Serializable {

    private String username;
    private String nickName;
    private Boolean enabledSon = Boolean.FALSE;
    private String userType;
    private Integer userLevel;
    private Date loginTime;
    private String onlineTime;
    private String loginIp;
    private String ipaddress;
    private String httpHeader;
    private Integer userId;
    private String parentPath;
    private Integer primaryId;


    public String getOnlineTime() {
        long time1=loginTime.getTime();
        long time2=(new Date()).getTime();
        onlineTime = DateUtil.formatBetween(time2-time1, BetweenFormater.Level.DAY.MINUTE);
        return onlineTime;
    }

}
