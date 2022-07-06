package com.xzy.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name="hm_menu_son")
@AllArgsConstructor
@NoArgsConstructor
public class HmMenuSon implements Serializable {
    private Integer sonId;
    private Integer menuId;
}
