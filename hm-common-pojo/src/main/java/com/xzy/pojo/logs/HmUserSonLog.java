package com.xzy.pojo.logs;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="hm_user_son_log")
public class HmUserSonLog implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer userId;//被修改人ID
    private String createUser;//创建用户
    private String createIp;//创建IP
    private String createType;//创建类型
    private String oldValue;//原始值
    private String newValue;//更新值
    private Date createTime;//创建时间
    private String createId;//创建用户ID
}
