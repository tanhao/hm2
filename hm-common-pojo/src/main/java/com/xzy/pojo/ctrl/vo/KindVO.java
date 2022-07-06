package com.xzy.pojo.ctrl.vo;

import java.util.List;
import java.util.Map;

import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.GroupDO;
import com.xzy.pojo.ctrl.KindDO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class KindVO {
    protected List<GroupDO> groups;
    protected Map<Integer, List<KindDO>> kinds;
    protected Map<Integer, List<LotteryVO>> lotterys;
    // Key:lotteryId,kindId
    protected Map<Integer,? extends Map<Integer, UserKindDO>> userKinds;
}