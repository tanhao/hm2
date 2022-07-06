package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.OddsVO;
import com.xzy.pojo.user.response.UserLotteryLimitRp;

import java.util.List;

public interface UserLimitService {
    List<UserLotteryLimitRp> getUserLotteryLimit(Integer userId, Integer lotteryId);
}