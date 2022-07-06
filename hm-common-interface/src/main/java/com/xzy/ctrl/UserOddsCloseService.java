package com.xzy.ctrl;

import java.util.Map;

public interface UserOddsCloseService {
    Map<Integer, Boolean>  listNowUserOddsClose(Integer userId, Integer lotteryId);
}