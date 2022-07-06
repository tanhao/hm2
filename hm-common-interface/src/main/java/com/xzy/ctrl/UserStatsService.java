package com.xzy.ctrl;

import com.xzy.pojo.ctrl.bo.UserStatsBO;

import java.util.List;

public interface UserStatsService {
    List<UserStatsBO> listUserStatsBOs(Integer userId, Integer lotteryId, String gameNo,String buhuo,String markets);
    List<UserStatsBO> listUserStatsBOsGroupByLotteryId(Integer userId,Integer companyId,String buhuo,String markets);
    Double getTodayWinLose(Integer userId);
}