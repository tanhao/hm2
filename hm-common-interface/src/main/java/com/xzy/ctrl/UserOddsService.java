package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsCloseRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.request.OddsPlusMinusRQ;
import com.xzy.pojo.ctrl.vo.OddsVO;

import java.util.Map;

public interface UserOddsService {
    OddsVO getOddsVO(Integer userId);
    OddsVO getAgent1ORL(Integer userId);

    OddsRegressLimitFulloddsRQ updateORCLHoutong(OddsRegressLimitFulloddsRQ orlf);
    OddsRegressLimitFulloddsRQ updateORCLCompany(OddsRegressLimitFulloddsRQ orlf);
    OddsRegressLimitFulloddsRQ updateCompanyORLF(OddsRegressLimitFulloddsRQ orlf);
    OddsRegressLimitFulloddsRQ updateAgent1ORL(OddsRegressLimitFulloddsRQ orlf);
    OddsRegressLimitFulloddsRQ updateAgent1RegressLimit(OddsRegressLimitFulloddsRQ orlf);

    CopyORLFRQ copyCompany(CopyORLFRQ copy);
    CopyORLFRQ copyAgent1(CopyORLFRQ copy);

    Map<Integer, double[]>  listNowUserOdds(Integer userId, Integer userLevel,Integer lotteryId, String market);


    OddsPlusMinusRQ updateUserOdds(OddsPlusMinusRQ pmo);
    Boolean updateUserOddsStatus(OddsCloseRQ oc);
}