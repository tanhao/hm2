package com.xzy.pojo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Table(name="hm_user_login_error")
public class HmUserLoginError implements Serializable{
    @Id
    @GeneratedValue(generator = "JDBC")
    @JsonIgnore
    @OrderBy("DESC")
    private Integer id;
    private String username;
    @JsonIgnore
    private Integer userId;
    private String logType;
    private String createIp;
    private String createBy;
    private LocalDate createDay;
    private Date createTime;

}
