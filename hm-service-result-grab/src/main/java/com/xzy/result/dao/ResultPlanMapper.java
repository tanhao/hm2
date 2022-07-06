package com.xzy.result.dao;

import com.xzy.result.vo.LotteryPlanVo;
import com.xzy.result.vo.ResultKillVo;
import com.xzy.result.vo.ResultPlanVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResultPlanMapper extends Mapper<ResultPlanVo> {

    List<ResultPlanVo> findPlanByDay(@Param("lotteryKey")String lotteryKey,@Param("dateStr") String dateStr);

    List<ResultKillVo> findKillByDay(@Param("lotteryKey")String lotteryKey, @Param("dateStr") String dateStr);

    int findKillByDayCount(@Param("lotteryKey")String lotteryKey, @Param("dateStr") String dateStr);

    ResultPlanVo findByPlanByLot(@Param("lotteryKey")String lotteryKey);

    List<LotteryPlanVo> findLotteryPlan();

}
