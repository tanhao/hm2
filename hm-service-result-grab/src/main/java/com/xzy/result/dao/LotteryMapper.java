package com.xzy.result.dao;

import com.xzy.result.vo.LotteryVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LotteryMapper extends Mapper<LotteryVo> {

    List<LotteryVo> selectByAllList();

    Integer updateLotterySort(LotteryVo lotteryVo);
}
