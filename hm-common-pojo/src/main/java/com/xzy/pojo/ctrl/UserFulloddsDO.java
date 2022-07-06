package com.xzy.pojo.ctrl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString

public class UserFulloddsDO extends UserCategoryDO  implements Serializable {
    protected Double odds;
}