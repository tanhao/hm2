package com.xzy.ctrl;

import com.xzy.enums.EOpenModel;
import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;

import java.util.List;

public interface LotteryService {
    List<LotteryVO> selectLotteryByCompanyId(Integer companyId);
    List<LotteryVO> listLotterys();
    Boolean updateDelay( Integer lotteryId, Integer delayOpen, Integer precedeClose, Integer zmPrecedeClose);
    Boolean updateOrdering(List<LotteryDO> lotterys);
    Boolean updateOpenModel(Integer lotteryId, EOpenModel openModel);
}