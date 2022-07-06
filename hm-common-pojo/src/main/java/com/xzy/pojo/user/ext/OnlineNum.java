package com.xzy.pojo.user.ext;

import lombok.Data;

import java.io.Serializable;

@Data
public class OnlineNum implements Serializable {
    private Integer agent;
    private Integer company;
    private Integer member;
    private Integer sub;
    private Integer loginLvl;
    private Boolean isSub;

}