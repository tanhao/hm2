package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserLotteryDO;
import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.UserWarnDO;
import com.xzy.pojo.ctrl.bo.UserNoticeBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserNoticeMapper {
    List<UserNoticeBO> listUserNotices(@Param("userId")Integer userId,@Param("start") Integer start, @Param("end")Integer end);
    Integer getUserNoticeTotal(@Param("userId")Integer userId);

    List<UserNoticeBO> listShowNotice(@Param("type")String type,@Param("userId")Integer userId,@Param("start") Integer start, @Param("end")Integer end);
    Integer getShowNoticeTotal(@Param("type")String type,@Param("userId")Integer userId);

    Integer updateUserNotice(@Param("notice") UserNoticeDO notice);

    Integer deleteUserNotice(@Param("userId") Integer userId,@Param("id") Integer id);

    Integer updateUserNoticeState(@Param("userId") Integer userId,@Param("id") Integer id,@Param("type") String type,@Param("val") Integer val);

    Integer saveUserNotice(@Param("notice") UserNoticeDO notice);
}
