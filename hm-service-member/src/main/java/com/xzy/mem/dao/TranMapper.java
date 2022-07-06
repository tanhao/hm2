package com.xzy.mem.dao;

import com.xzy.pojo.mem.BetRp;
import com.xzy.pojo.mem.BetTime;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TranMapper extends Mapper<BetRp> {

    Integer execBet(BetRp betRp);

    Integer calculateStats(BetRp betRp);

    BetTime selectBetTime(@Param("lotteryId") Integer lotteryId,
                          @Param("userId") Integer userId,
                          @Param("gameNo") Long gameNo);

}
