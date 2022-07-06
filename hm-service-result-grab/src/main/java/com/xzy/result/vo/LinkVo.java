package com.xzy.result.vo;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_link")
public class LinkVo {

    @Id
    private Integer id;
    private String linkKey;
    private String linkName;
}
