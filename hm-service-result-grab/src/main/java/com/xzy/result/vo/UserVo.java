package com.xzy.result.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_user")
public class UserVo {

    @Id
    private Integer id;
    private String userName;
    private String userPwd;
    private Integer userLvl;
    private Integer pwdInit;
}
