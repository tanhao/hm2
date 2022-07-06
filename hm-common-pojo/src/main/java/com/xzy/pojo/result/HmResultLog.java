package com.xzy.pojo.result;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table
public class HmResultLog implements Serializable {
    @Id
    public Integer id;
    public Integer resultId;
    public String createType;
    public Date createTime;
    public String createUser;
    public String createIp;
    public Integer createId;
    public Date createDay;
    public String requestBody;
    public String responseData;

}
