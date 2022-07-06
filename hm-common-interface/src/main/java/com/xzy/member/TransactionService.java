package com.xzy.member;

import com.xzy.pojo.mem.BetRq;

import java.util.Date;
import java.util.Map;

public interface TransactionService {
    Date checkBetTime(Integer lotteryId, Integer userId, Long gameNo);

    Map execQueueBet(Integer userId, BetRq betRq);
}
