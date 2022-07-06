package com.xzy.result.service;


import com.xzy.result.dao.ResultLogMapper;
import com.xzy.result.vo.ResultLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultLogService {

    @Autowired
	ResultLogMapper resultLogMapper;

	public List<ResultLogVo> findResultLogByDay(String day) {
		String sDate = day+" 00:00:00";
		String eDate = day+" 23:59:59";
		return resultLogMapper.findResultLogByDay(sDate,eDate);
	}

	public void saveResultLog(String vOld,String vNew,String updateBy) {
		ResultLogVo resultLogVo = new ResultLogVo();
		resultLogVo.setType("添加赛果");
		resultLogVo.setVOld(vOld);
		resultLogVo.setVNew(vNew);
		resultLogVo.setUpdateBy(updateBy);
		resultLogMapper.insertSelective(resultLogVo);
	}
}
