package com.xzy.pojo.result;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "hm_result")
public class HmResult implements Serializable {
    @Id
    private Integer id;
    private Integer lotteryId;
    private String lotteryKey;
    private String  gameNo;
    private String result;
    private Integer openTime;
    private Integer actionTime;
    private Integer dividendTime;
    private Integer createTime;
    private Integer updateTime;
    private String updateBy;
    private String status;
    private LocalDate resultDay;
    private String stats;
    private LocalDate accountDay;
}
