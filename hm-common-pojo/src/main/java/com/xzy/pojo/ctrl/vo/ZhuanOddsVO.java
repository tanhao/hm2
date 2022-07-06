
package com.xzy.pojo.ctrl.vo;

import com.xzy.enums.EJumpType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class ZhuanOddsVO extends CategoryVO {
    private Map<String,Boolean> markets;
}