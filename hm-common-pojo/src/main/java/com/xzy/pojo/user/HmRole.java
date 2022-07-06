package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name="hm_role")
public class HmRole implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String remark;
    private Boolean status;
    @OrderBy
    private Integer userLevel;

    @Transient
    private Integer[] menuIds;


}
