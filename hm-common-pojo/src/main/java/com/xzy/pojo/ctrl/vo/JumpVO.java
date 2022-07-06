
package com.xzy.pojo.ctrl.vo;

import com.xzy.enums.EJumpType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JumpVO extends KindVO {
    private EJumpType jumpType;
}