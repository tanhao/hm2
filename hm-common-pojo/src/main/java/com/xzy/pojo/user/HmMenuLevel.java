package com.xzy.pojo.user;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="hm_menu_level")
public class HmMenuLevel implements Serializable {
    @Id
    private Integer userLevel;
    private Integer menuId;

}
