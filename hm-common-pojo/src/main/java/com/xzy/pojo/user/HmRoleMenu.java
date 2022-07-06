package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name="hm_role_menu")
public class HmRoleMenu implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer roleId;
    private Integer menuId;

    @Transient
    private Integer[] menuIds;

}
