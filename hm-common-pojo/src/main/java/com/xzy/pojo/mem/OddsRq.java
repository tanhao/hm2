package com.xzy.pojo.mem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class OddsRq implements Serializable {
    @NotNull(message = "陪率ID不能为空")
    private Integer oddsId;
    @NotNull(message = "陪率不能为空")
    private BigDecimal odds;
    @NotNull(message = "下注金额不能为空")
    @DecimalMin("1")
    private BigDecimal betAmt;
    private String betContent = "";

}
