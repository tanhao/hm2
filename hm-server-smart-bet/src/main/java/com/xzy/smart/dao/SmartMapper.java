package com.xzy.smart.dao;

import com.xzy.pojo.result.HmResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Marker;

public interface SmartMapper extends Marker {

    HmResult selectCurrentGameNo(@Param("companyId")Integer companyId,@Param("lotteryId") Integer lotteryId,@Param("accountDay")String accountDay);

    Boolean checkOrderWaiting(@Param("userId") Integer userId,@Param("gameNo") String gameNo);

    String selectOddss(@Param("playId") Integer playId,@Param("oddsKeys") String[] oddsKeys);

}
