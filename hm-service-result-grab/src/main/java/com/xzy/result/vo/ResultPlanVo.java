package com.xzy.result.vo;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Table(name = "t_result_plan")
public class ResultPlanVo implements Serializable {

    @Id
    private Integer id;
    private String lotteryKey;
    private String gameNo;
    private String planA;
    private String planB;
    private String planC;
    private int costA;
    private int costB;
    private int costC;
    private int costAllA;
    private int costAllB;
    private int costAllC;
    private int profitA;
    private int profitB;
    private int profitC;
    private int winA;
    private int winB;
    private int winC;
    private int planCount;
    private String winAno;
    private String winBno;
    private String winCno;

    @Transient
    private String result;
    @Transient
    private String actionTime;
}
