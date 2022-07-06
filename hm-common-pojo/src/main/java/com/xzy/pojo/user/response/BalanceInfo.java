package com.xzy.pojo.user.response;

import com.xzy.pojo.user.HmUserBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class BalanceInfo {
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户等级")
    private Integer userLevel;
    @ApiModelProperty(value = "用户初始余额")
    private BigDecimal balance;// credit_balance
    @ApiModelProperty(value = "用户使用余额")
    private BigDecimal useBalance;// useBalance
    @ApiModelProperty(value = "上级账号余额")
    private BigDecimal superBalance;// use_credit_balance
    @ApiModelProperty(value = "上级账号使用余额")
    private BigDecimal superUseBalance;// superUseBalance
    @ApiModelProperty(value = "账号类型")
    private String reserveModel;
    private BigDecimal recBalance;
    private String userType;

    private Boolean secondary;
    private String superReserveModel;
    private String superUsername;
    private Integer superUserLevel;
    private BigDecimal useRecBalance;
    private BigDecimal superRecBalance;
    private BigDecimal superUseRecBalance;
}
