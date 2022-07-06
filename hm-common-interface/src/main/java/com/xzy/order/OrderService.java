package com.xzy.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.order.HmOrder;
import com.xzy.pojo.order.request.OrderAgRq;
import com.xzy.pojo.order.request.OrderMemRq;
import com.xzy.pojo.order.request.WinLoseRq;
import com.xzy.pojo.order.response.BhAmtRp;
import com.xzy.pojo.order.response.DetailTimeCount;
import com.xzy.pojo.order.response.WinLoseInfo;

import java.util.List;


public interface OrderService {
    List<HmOrder> findByMember(OrderMemRq orderMemRq);

    List<Long> findGameNo(Integer lotteryId, String day);

    List<HmOrder> findOrderByAgentId(OrderAgRq orderAgRq);

    HmOrder findOrderByOrderId(String orderId, Integer agentId);

    List<WinLoseInfo> selectWinLose(WinLoseRq winLoseRq);

    BhAmtRp selectBhWinLose(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseByLottery(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectBhWinLoseByLottery(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectKind(WinLoseRq winLoseRq);

    JSONObject getTwoWeeksReport(Integer userId, Integer lotteryId, String winOrLoserState);

    JSONArray getLotteryReport(Integer userId, String localDate, String winOrLoserState);

    void voidOrderByOrderId(List<String> orderIds,String voidReason);

    void voidOrderByUser(Integer lotteryId, Long gameNo, String username, String voidReason,String voidType);

    List<DetailTimeCount> selectDetailTimeCount(WinLoseRq search);
}
