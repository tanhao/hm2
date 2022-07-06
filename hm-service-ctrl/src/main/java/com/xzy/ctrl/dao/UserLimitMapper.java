package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.bo.UserRegressBO;
import com.xzy.pojo.ctrl.request.DiffRegressRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.user.response.UserLotteryLimitRp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLimitMapper {
    List<UserLotteryLimitRp> getUserLotteryLimit(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId);
}
