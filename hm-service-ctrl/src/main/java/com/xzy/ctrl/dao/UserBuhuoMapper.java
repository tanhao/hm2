package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.UserBuhuoDO;

import com.xzy.pojo.ctrl.bo.UserBuhuoStatsBO;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserBuhuoMapper {
    List<UserBuhuoDO> listUserBuhuoDOs(Integer userId);
    List<UserBuhuoStatsBO> listUserBuhuoStats(Integer userId);
    void updateBuhuo(BuhuoRQ buhuo);

    Integer getManualBuhuoAmt(ManualBuhuoRQ buhuo);
    void manualBuhuo(ManualBuhuoRQ buhuo);
}
