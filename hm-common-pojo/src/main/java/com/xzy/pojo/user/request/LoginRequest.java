package com.xzy.pojo.user.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest  implements Serializable {

    private String username;
    private String password;
    private String verifyCode;
    private String loginMode;
    private String loginType;

}
