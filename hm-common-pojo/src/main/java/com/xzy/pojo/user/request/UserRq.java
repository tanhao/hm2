package com.xzy.pojo.user.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRq implements Serializable {
    private int page=1;
    private int size=10;
    private String reserveModel;
    private String status;
    private String username;
    private Integer parentId;
    private Integer userLevel;
    private String userType;
    private Integer userId;
    private String securityCode;
    private Integer loginId;
}
