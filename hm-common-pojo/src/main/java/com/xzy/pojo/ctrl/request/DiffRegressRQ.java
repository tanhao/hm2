package com.xzy.pojo.ctrl.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class DiffRegressRQ  implements Serializable {
    private Integer userId;
    private Float diff;
    private Boolean hasOrder;

    private Integer errcode;
    private String errmsg;

    private String updateIp;
    private String updateBy;


}
