package com.xzy.pojo.smart;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "hm_location_plan")
public class HmLocationPlan implements Serializable {
    @Id
    private Integer Id;
    private Integer lotteryId;
    private Integer planId;
    private Integer userId;
    private Integer playId;
    private String numbers;
    private Integer amount;
    private String oddss;
}
