package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.*;
import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserRegressBO;
import com.xzy.pojo.user.HmUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class OddsVO {
    protected List<GroupDO> groups;
    protected Map<Integer, List<KindDO>> kinds;
    protected Map<Integer, List<CategoryDO>> categorys;
    protected Map<Integer, List<LotteryVO>> lotterys;
    // Key:lotteryId,kindId
    protected Map<Integer,Map<Integer,UserRegressBO>> regress;
    // Key:lotteryId,categoryId
    protected Map<Integer,Map<Integer,UserOddsBO>> odds;

    protected Map<String,Boolean> markets;

    protected List<QuickSettingDO> quickSetting;

    protected Boolean hasLower;

    protected List<HmUser> lowers;

    protected  Boolean hasOrder;

    protected  Boolean isStandard;
}