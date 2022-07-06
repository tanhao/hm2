package com.xzy.result.dao;

import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.vo.ResultCountVo;
import com.xzy.result.vo.ZkcVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Async;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface HmResultMapper extends Mapper<HmResult> {

    void saveResult(ResultGrab resultGrab);

    List<HmResult> findTotalResult(String accountDay);

    List<ResultCountVo> selectCountByResultDay(String accountDay);

    List<ResultCountVo> selectCountByAccountDay(String accountDay);

    List<HmResult> selectNoResultByLottery(@Param("lotteryId") Integer lotteryId, @Param("day") String day);

    List<HmResult> selectNoAccountByLottery(@Param("lotteryId") Integer lotteryId, @Param("day") String day);

    int cancelOrder(@Param("lotteryId") Integer lotteryId, @Param("gameNo") String gameNo);

    void spPayoutPrize(Map<String, Object> maps);

    @Async
    void spBackOrder(@Param("lotteryInfo") String lotteryInfo);

    void spPayoutPrizeRepeat(Map<String, Object> maps);

    void addResult(@Param("result") List<HmResult> result);

    List<HmResult> selectNoDividend();

    HmResult selectNoResultByZkc(Integer lotteryId);

    LotteryDO selectLottery(Integer lotteryId);

    void spPayoutPk10Zk(ZkcVo zkcVo);

    void spPayoutSscZk(ZkcVo zkcVo);
}
