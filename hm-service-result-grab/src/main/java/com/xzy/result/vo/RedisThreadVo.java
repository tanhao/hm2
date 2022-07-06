package com.xzy.result.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisThreadVo {

    private String lotteryKey;
    private String url;
    private String ipKey;
    private Integer status;  //0关闭，1启动，4异常

}
