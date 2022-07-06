package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserOddsCljpDO;
import com.xzy.pojo.ctrl.UserOddsNowDO;
import com.xzy.pojo.ctrl.bo.UserOddsCljpBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOddsCljpMapper {
    List<UserOddsCljpBO> listUserOddsCljpDOs(@Param("userId")Integer userId, @Param("lotteryId")Integer lotteryId);

}
