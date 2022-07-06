package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserOddsNowDO;
import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserOddsNowBO;
import com.xzy.pojo.ctrl.bo.UserZhuanOddsBO;
import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOddsNowMapper {
    List<UserOddsNowBO> listUserOddsNowDOs(@Param("userId")Integer userId, @Param("lotteryId")Integer lotteryId, @Param("market")String market);
}
