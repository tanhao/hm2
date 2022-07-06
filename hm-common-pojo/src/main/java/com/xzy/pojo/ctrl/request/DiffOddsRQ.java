package com.xzy.pojo.ctrl.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class DiffOddsRQ  implements Serializable {
    private Integer userId;
    private Float diff;

    private Integer errcode;
    private String errmsg;

    private String updateIp;
    private String updateBy;

}
