package com.xzy.pojo.user.request;

import com.xzy.enums.UserEnums;
import com.xzy.pojo.user.HmUserBase;
import com.xzy.pojo.user.HmUserPrePt;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString(callSuper = true)
public class UpUserBalanceRq {
    private String operation;
    private Integer userId;
    private BigDecimal amount;
    private UserEnums.ReserveModel reserveModel;
}
