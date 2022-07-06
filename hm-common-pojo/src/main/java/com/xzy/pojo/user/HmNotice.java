package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "hm_notice")
public class HmNotice implements Serializable {
    @Id
    private Integer id;
    private Integer userId;
    private String type;
    private String content;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private String createBy;
    private Integer ordering;


}
