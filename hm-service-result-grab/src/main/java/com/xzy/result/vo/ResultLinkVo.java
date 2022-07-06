package com.xzy.result.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Getter
@Setter
@ToString
@Table(name="t_result_link")
public class ResultLinkVo {
    @Id
    private String lotteryKey;//彩票种类
    @Id
    private String gameNo;
    @Id
    private String https; //网址类型
    private String result;  //结果
    private Long createTime;//创建时间
    private String dayTime;//创建时间
    private Integer secondTime;//与开彩差几秒
    private String createIp;//创建ip
    private Date accountDay;
}
