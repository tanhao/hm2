package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserOddsCloseDO;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOddsCloseMapper {
    List<UserOddsCloseDO> listUserOddsCloseDOs(@Param("userId") Integer userId, @Param("lotteryId") Integer lotteryId);

}
