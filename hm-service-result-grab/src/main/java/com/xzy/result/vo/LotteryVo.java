package com.xzy.result.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Table(name = "t_lottery")
public class LotteryVo implements Serializable {
    @Id
    private Integer lotteryId;
    private String lotteryKey;
    private String lotteryName;
    private String startTime;
    private Integer invlSecond;
    private Integer totalRound;
    private Integer byDate;
    private Integer byOpen;
    private String codeNums;
    private Integer resultSize;
    private Integer groupId;
    @Transient
    private Integer hotSort;
    @Transient
    private Integer hotChk;

}
