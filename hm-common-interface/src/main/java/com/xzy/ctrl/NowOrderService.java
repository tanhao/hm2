package com.xzy.ctrl;

import java.util.List;
import java.util.Map;

import com.xzy.enums.EBuhuoType;
import com.xzy.pojo.ctrl.OddsDO;
import com.xzy.pojo.ctrl.UserOddsDO;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import com.xzy.pojo.ctrl.vo.NowOrderVO;

public interface NowOrderService {
     NowOrderVO getFrontOdds(Integer userId,Integer lotteryId);
     NowOrderVO getFrontNowStats(Integer userId, Integer lotteryId);

     NowOrderVO getNowOrderVO(Integer loginUserId, Integer userId, Integer lotteryId, String market, String markets, String buhuo);
     NowOrderVO getNowLotteryVO(Integer userId, Integer lotteryId,String market);
     NowOrderVO getNowStats(Integer userId, Integer lotteryId,String gameNo,String buhuo,String markets);
     NowOrderVO getNowOdds(Integer userId, Integer lotteryId,String gameNo,String buhuo,String market,String markets);

     Integer getManualBuhuoAmt(ManualBuhuoRQ buhuo);
     void manualBuhuo(ManualBuhuoRQ buhuo);
}