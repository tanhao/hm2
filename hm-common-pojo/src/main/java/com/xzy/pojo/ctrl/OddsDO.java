package com.xzy.pojo.ctrl;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OddsDO implements Serializable {
    private static final long serialVersionUID = 979483725642649661L;
    private Integer oddsId;
    private String oddsKey;
    private String oddsName;
    private Integer playId;
    private Integer categoryId;
    private Integer ordering;
}