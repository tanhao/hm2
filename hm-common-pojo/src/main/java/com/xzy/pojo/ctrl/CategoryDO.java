package com.xzy.pojo.ctrl;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CategoryDO implements Serializable {
    private static final long serialVersionUID = 2251122051266750978L;
    private Integer categoryId;
    private String categoryKey;
    private String categoryName;
    private Integer kindId;
    private Double odds;
    private Double step;
    private Integer decimals;
    private Integer ordering;
}