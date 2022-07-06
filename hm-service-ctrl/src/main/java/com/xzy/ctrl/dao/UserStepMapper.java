package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.UserStepDO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserStepMapper {
    List<UserStepDO> listUserStepDOs(Integer userId);
    List<UserStepDO> listUserStepDOsByLotteryId(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId);

    void updateStep(OddsRegressLimitFulloddsRQ orlf);
}
