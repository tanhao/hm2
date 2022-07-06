package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserStepDO;
import com.xzy.pojo.ctrl.bo.UserStatsBO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserStatsMapper {
    List<UserStatsBO> listUserStatsBOs(@Param("userId") Integer userId, @Param("lotteryId") Integer lotteryId,@Param("gameNo") String gameNo,@Param("buhuo") String buhuo,@Param("markets") String markets);
    List<UserStatsBO> listUserStatsBOsGroupByLotteryId(@Param("userId") Integer userId, @Param("companyId") Integer companyId,@Param("buhuo") String buhuo,@Param("markets") String markets);
    Double getTodayWinLose(Integer userId);
}
