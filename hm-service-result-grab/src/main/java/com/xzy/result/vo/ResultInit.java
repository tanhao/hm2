package com.xzy.result.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Table(name = "t_result_init")
public class ResultInit implements Serializable {
    @Id
    private Integer id;
    private String lotteryKey;
    private String gameNo;
    private Integer actionTime;
    private String result;
    private Long secondTime;  //获取结果用时
    private String link;        //获取结果的网址
    private String createIp;    //获取结果的ip

    @Transient
    private String actionTimeStr;
    @Transient
    private String lotteryName;
    @Transient
    private Integer rn;

}
