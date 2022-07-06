package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.OddsVO;

import java.util.Map;

public interface UserOddsNowService {
    Map<Integer, double[]>  listNowUserOddsNow(Integer userId,Integer userLevel, Integer lotteryId, String market);
}