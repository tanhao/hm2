package com.xzy.order.dao;

import com.xzy.pojo.order.HmOrder;
import com.xzy.pojo.order.request.OrderAgRq;
import com.xzy.pojo.order.request.OrderMemRq;
import com.xzy.pojo.order.request.WinLoseRq;
import com.xzy.pojo.order.response.BhAmtRp;
import com.xzy.pojo.order.response.DetailTimeCount;
import com.xzy.pojo.order.response.ReportRp;
import com.xzy.pojo.order.response.WinLoseInfo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import tk.mybatis.mapper.common.Mapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<HmOrder> {

    @Select("{CALL SP_CALCULATE_ORDER_DELIVERY()}")
    @Options(statementType = StatementType.CALLABLE)
    void splCalculateOrderDaily();

    @Select("{CALL SP_CALCULATE_ORDER_DAILY_BH()}")
    @Options(statementType = StatementType.CALLABLE)
    void splCalculateOrderDailyBh();

    @Select("{CALL SP_PAYOUT_PRIZE_CHECK()}")
    @Options(statementType = StatementType.CALLABLE)
    void splPayoutPrizeCheck();

    List<Long> findGameNo(@Param("lotteryId") Integer lotteryId, @Param("accountDay") String accountDay);

    List<HmOrder> findOrderByAgentId(OrderAgRq orderAgRq);

    HmOrder findOrderByOrderId(@Param("orderId") String orderId, @Param("agentId") Integer agentId);

    List<WinLoseInfo> selectWinLose(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseVoid(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseWaiting(WinLoseRq winLoseRq);

    BhAmtRp selectBhWinLose(WinLoseRq winLoseRq);

    BhAmtRp selectBhWinLoseWaiting(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseByLottery(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseByLotteryVoid(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectWinLoseByLotteryWaiting(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectBhWinLoseByLottery(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectBhWinLoseByLotteryWaiting(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectKind(WinLoseRq winLoseRq);
    List<WinLoseInfo> selectKindVoid(WinLoseRq winLoseRq);

    List<WinLoseInfo> selectKindWaiting(WinLoseRq winLoseRq);

    List<ReportRp> getDayReports(@Param("userId") Integer userId, @Param("accountDay") LocalDate accountDay, @Param("lotteryId") Integer lotteryId, @Param("winOrLoserState") String winOrLoserState);

    List<ReportRp> getDayReportsTwo(@Param("userId") Integer userId, @Param("accountDay") LocalDate accountDay);

    List<ReportRp> getLotteryReports(@Param("userId") Integer userId, @Param("accountDay") String accountDay, @Param("winOrLoserState") String winOrLoserState);

    @Select("select account_day from hm_order where lottery_id=#{lotteryId} and game_no = #{gameNo}  AND STATUS='DIVIDEND' limit 1")
    Date selectAccountByGameNo(@Param("lotteryId") Integer lotteryId, @Param("gameNo") Long gameNo);

    void voidOrderByUser(Map<String, Object> map);

    List<HmOrder> findByMember(OrderMemRq search);

    List<DetailTimeCount> selectDetailTimeCount(WinLoseRq search);

    List<DetailTimeCount> selectDetailTimeCountVoid(WinLoseRq search);

    List<DetailTimeCount> selectDetailTimeCountWaiting(WinLoseRq search);
}
