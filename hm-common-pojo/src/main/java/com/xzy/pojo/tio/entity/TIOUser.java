package com.xzy.pojo.tio.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TIOUser implements Serializable {
    private Integer userId;
    private Integer primaryId;
    private List<String> userIds;
    private String username;
    private String userType;
    private Integer lotteryId;
    private String lotteryKey;
    private String market;
}
