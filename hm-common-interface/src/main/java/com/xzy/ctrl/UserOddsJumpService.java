package com.xzy.ctrl;

import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;

import java.util.List;
import java.util.Map;

public interface UserOddsJumpService {
    Map<Integer, double[]>  listNowUserOddsJump(Integer userId, Integer userLevel,Integer lotteryId);
    Map<Integer, Map<Integer, List<UserOddsJumpBO>>> listNowOddsJump(Integer updateTime);
}