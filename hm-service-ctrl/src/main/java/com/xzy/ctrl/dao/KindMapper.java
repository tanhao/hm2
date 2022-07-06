package com.xzy.ctrl.dao;

import java.util.List;
import com.xzy.pojo.ctrl.KindDO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KindMapper {
    List<KindDO> listKinds();
    List<KindDO> listKindsByByLotteryId(Integer lotteryId);
}
