
package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class CljpVO {

    private Map<Integer, List<KindDO>> kinds;
    private List<LotteryVO> lotterys;
    private Integer kindId;
    private Integer lotteryId;
    private List<UserCljpDO> userKinds;

}