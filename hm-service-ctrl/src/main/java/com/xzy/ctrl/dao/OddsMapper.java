package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.OddsDO;
import com.xzy.pojo.ctrl.PlayDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OddsMapper {
    List<OddsDO> listOddss();
    List<OddsDO> listOddssByLotteryId(Integer lotteryId);
}
