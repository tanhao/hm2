package com.xzy.mem.dao;

import com.xzy.pojo.mem.BetRp;
import com.xzy.pojo.result.response.ResultRp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface RoadMapper {

    String getGroupKey(@Param("lotteryId") Integer lotteryId);

    List<ResultRp> getResultList(@Param("lotteryId") Integer lotteryId, @Param("accountDay") String accountDay);
}
