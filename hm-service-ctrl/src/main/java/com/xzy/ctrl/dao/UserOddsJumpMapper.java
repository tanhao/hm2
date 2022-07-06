package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserOddsJumpDO;
import com.xzy.pojo.ctrl.UserOddsNowDO;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
import com.xzy.pojo.ctrl.bo.UserOddsNowBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOddsJumpMapper {
    List<UserOddsJumpBO> listUserOddsJumpDOs(@Param("userId")Integer userId, @Param("lotteryId")Integer lotteryId);
    List<UserOddsJumpBO> listOddsJumpDOs(@Param("updateTime")Integer updateTime);
    Integer getDBNow();

}
