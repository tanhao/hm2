package com.xzy.smart.dao;

import com.xzy.pojo.smart.HmPlan;
import com.xzy.smart.vo.StopVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HmPlanMapper extends Mapper<HmPlan> {
    @Select("SELECT * FROM hm_plan where plan_status=1 ORDER BY plan_id FOR UPDATE SKIP LOCKED")
    List<HmPlan> selectPlan();

    @Select(" SELECT  hu.user_id,hu.stop_profit,hu.stop_loss,IFNULL(SUM(hod.bet_win_amt),0) bet_win_amt " +
            " FROM hm_manage.hm_user_base hu  " +
            " JOIN hm_order.hm_order_daily_delivery hod ON hu.stop_profit >0 AND hu.stop_loss >0 " +
            " and hod.user_id = hu.user_id AND hod.account_day = #{day} AND hu.user_id = #{userId} GROUP BY hu.user_id")
    StopVo selectStopData(@Param("day") String day, @Param("userId") Integer userId);

}
