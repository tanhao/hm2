package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Table(name="hm_user_running")
public class HmUserRunning implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer userId;
    private String category;
    private String operation;
    private String username;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDate createDay;
    @OrderBy("DESC")
    private Date createDateTime;
    private String createTime;
    private String createBy;
    private String refId;


}
