package com.xzy.ctrl;

import java.util.Map;

public interface UserOddsCljpService {
    Map<Integer, double[]>  listNowUserOddsCljp(Integer userId,Integer userLevel, Integer lotteryId);
}