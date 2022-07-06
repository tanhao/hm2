package com.xzy.pojo.user.response;

import com.xzy.pojo.user.HmUserBase;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class UserInfo extends HmUserBase {
    //是否在线
    private Boolean online;
    //是否APP登录
    private Boolean app;
    private String username;
    private String nickName;
    private String parentName;
    private Integer parentId;
    private String status;
    private Integer userLevel;
    private Date created;
    private String userType;
    private String loginIpBound;
    private Integer allAgentCount;
    private Integer allMemberCount;
    private Integer agentCount;
    private Integer memberCount;
    private String securityCode;
    private String siteName;
    private String superGainOdds;
    private BigDecimal memberCreditBalance;//会员信用
    private BigDecimal memberUseCreditBalance;//会员可用信用
    private BigDecimal memberRechargeBalance;//会员充值
    private BigDecimal memberUseRechargeBalance;//会员可用充值

    private BigDecimal agentCreditBalance;
    private BigDecimal agentUseCreditBalance;
    private BigDecimal agentRechargeBalance;
    private BigDecimal agentUseRechargeBalance;

    private Boolean disk;
    private Boolean bhStatus;
    private Boolean betSlip;//是否有注单
    private Boolean betWaiting;//是否有未派注单
    private String pGainOdds;//上级的
    private Integer passwordError;
}
