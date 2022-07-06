package com.xzy.pojo.ctrl;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GroupDO implements Serializable {
    private static final long serialVersionUID = 6600807383465932106L;
    private Integer groupId;
    private String groupKey;
    private String groupName;
    private Integer ordering;
}