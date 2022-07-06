package com.xzy.result.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultVo {

    private int id;
    private String lotteryKey;//彩票种类
    private String gameNo; //期号
    private String https; //网址类型
    private String result;  //结果

    private int counts;


}
