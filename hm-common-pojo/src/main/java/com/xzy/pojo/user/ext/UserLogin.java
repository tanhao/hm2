package com.xzy.pojo.user.ext;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLogin implements Serializable {
    private Integer userId;
    private String username;
    private String nickName;
    private String userType;
    private Integer companyId;
    private Integer oddsAgentId;
    private Integer userLevel;
    private String loginIp;
    private Date loginTime;
    private String loginMode;
    private Boolean enabledSon = Boolean.FALSE;
    private String status;
    private String loginIpBound;
    @JsonIgnore
    private String password;
    private Integer primaryId;
    private String loginDevice;
    private String parentPath;
    private Integer parentId;
    private String uid;
    private String onlineTime;
    private String marketSelect;
    @JsonIgnore
    private String httpHeader;
    private String marketOpen;
    private Integer passwordError;
    private Integer resetPassword;

    @JsonIgnore
    public String getOnlineTime() {
        long time1=loginTime.getTime();
        long time2=(new Date()).getTime();
        onlineTime = DateUtil.formatBetween(time2-time1, BetweenFormater.Level.DAY.MINUTE);
        return onlineTime;
    }

    /**
     * 判断是否为管理员
     *
     * @return
     */
    @JsonIgnore
    public Boolean getAdmin() {
        return ObjectUtil.equal(userLevel, 0);
    }

    /**
     * 判断是否为公司
     *
     * @return
     */
    public Boolean getHouTong() {
        return ObjectUtil.equal(userLevel, 1);
    }

    /**
     * 判断是否为公司
     *
     * @return
     */
    public Boolean getCompany() {
        return ObjectUtil.equal(userLevel, 2);
    }

    /**
     * 子账号获取主账号id
     *
     * @return
     */
    public Integer getPrimaryId() {
        if (!enabledSon) {
            return userId;
        }
        return primaryId;
    }

}