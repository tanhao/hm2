package com.xzy.pojo.ctrl.request;


import com.xzy.pojo.ctrl.UserCljpDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class LogRQ implements Serializable {
    private Integer userId;
    private Integer startTime;
    private Integer endTime;
    private Integer lotteryId;
    private Integer kindId;

    private Integer page=1;
    private Integer pageSize=20;
    private Integer pageStart=0;

    private Integer categoryId;
    private Integer oddsId;
    private Integer playId;
    private String gameNo;

    public Integer getPageStart(){
        return (this.page-1)*this.pageSize;
    }

}
