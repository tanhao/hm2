package com.xzy.pojo.user.request;

import com.xzy.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class userLoginRq implements Serializable {

    private String username;
    private String createIp;
    private String startTime = DateUtil.getAccountDay().toString();
    private String endTime = DateUtil.getAccountDay().toString();
    private Integer userId;
    //页码
    private Integer page = 1;
    //每页查询的个数
    private Integer size = 10;

}
