package com.xzy.pojo.mem;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Slf4j
public class BetRq implements Serializable {
    @NotNull(message = "彩票ID不能为空")
    private Integer lotteryId;
    @NotNull(message = "期号不能为空")
    private Long gameNo;
    @NotEmpty(message = "下注陪率不能为空")
    @Valid
    private List<OddsRq> betOdds;
    private Integer autoNewOdds = 1;
    private String betSource;
    private Date resultAccountDay;

    @Override
    public String toString() {
        return "投注参数{" +
                "lotteryId=" + lotteryId +
                ", gameNo=" + gameNo +
                ", autoNewOdds=" + autoNewOdds +
                ", betSource='" + betSource +
                ", resultAccountDay=" + resultAccountDay +
                '}';
    }
}