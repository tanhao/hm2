package com.xzy.pojo.logs.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class hmLogsInfo {
    private Integer id;
    private Integer userId;//被修改人ID
    private String createUser;//创建用户
    private String createIp;//创建IP
    private String createType;//创建类型
    private String oldValue;//原始值
    private String newValue;//更新值
    private Date createTime;//创建时间
    private String createId;//创建用户ID
    private String address;
}
