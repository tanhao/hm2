package com.xzy.result.vo;

import com.xzy.result.util.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageUtilVo implements Serializable {
    private Integer page=1;
    private Integer size=10;
    private String startTime = DateUtil.getToday();
    private String endTime = DateUtil.getToday();
    private String lotteryKey;

}
