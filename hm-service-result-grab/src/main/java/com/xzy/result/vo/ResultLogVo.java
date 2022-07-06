package com.xzy.result.vo;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_result_log")
public class ResultLogVo {

    @Id
    private Integer id;
    private String type;
    private String vOld;
    private String vNew;
    private String updateBy;
    private String updateTime;
}
