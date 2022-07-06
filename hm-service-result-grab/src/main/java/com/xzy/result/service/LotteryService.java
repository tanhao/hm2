package com.xzy.result.service;


import com.xzy.result.dao.LotteryMapper;
import com.xzy.result.vo.LotteryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LotteryService {

    @Autowired
	LotteryMapper lotteryMapper;

	public List<LotteryVo> findByOpen() {
		Example example = new Example(LotteryVo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("byOpen", 1);
		return lotteryMapper.selectByExample(example);
	}

	public List<LotteryVo> findLotteryByAll() {
		return lotteryMapper.selectByAllList();
	}

	public Integer updateLotterySort(LotteryVo lotteryVo) {
		return lotteryMapper.updateLotterySort(lotteryVo);
	}
}
