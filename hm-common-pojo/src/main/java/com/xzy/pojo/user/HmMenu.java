package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Table(name = "hm_menu")
public class HmMenu implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer menuId;
    private String code;
    private Integer parentId;
    private String menuName;
    private String url;
    private Integer mtype;
    private Integer level;
    @OrderBy
    private Integer sort;
    private Boolean status;
    private String icon;
    @Transient
    private Boolean checked = Boolean.FALSE;
    @Transient
    private Integer subCount;
    @Transient
    private List<HmMenu> children;

}
