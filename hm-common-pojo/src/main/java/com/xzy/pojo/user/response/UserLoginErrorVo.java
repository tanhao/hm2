package com.xzy.pojo.user.response;

import lombok.Data;

@Data
public class UserLoginErrorVo {
    private String username;
    private String status;
    private Integer passwordError;
    private Integer countError;
    private Integer unlockCount;
    private String lvl;
    private String userType;

}
