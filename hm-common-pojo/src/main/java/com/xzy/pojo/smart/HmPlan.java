package com.xzy.pojo.smart;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "hm_plan")
public class HmPlan implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer planId;
    private Integer userId;
    private Integer companyId;
    private Boolean planStatus;
    private String planName;
    private Integer lotteryId;
    private String gameNo;

}
