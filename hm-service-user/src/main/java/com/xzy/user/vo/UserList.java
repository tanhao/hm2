package com.xzy.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserList {
    private String username;
    private String parentName;
    private String nickName;
    private BigDecimal balance;
    private String marketOpen;

}
