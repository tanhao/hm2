package com.xzy.pojo.user;

import com.xzy.enums.UserEnums;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Table(name="hm_user_login")
public class HmUserLogin implements Serializable{
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String username;
    private Integer userId;
    private String logType;
    private String createIp;
    private LocalDate createDay;
    @OrderBy("DESC")
    private Date createTime;
    private String info;
    private String params;
    private String address;
}
