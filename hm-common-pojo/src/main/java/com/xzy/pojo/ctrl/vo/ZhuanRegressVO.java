
package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.QuickSettingDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class ZhuanRegressVO extends KindVO {
    private Boolean hasOrder;
    private Map<String,Boolean> markets;
    private List<QuickSettingDO> quickSetting;
}