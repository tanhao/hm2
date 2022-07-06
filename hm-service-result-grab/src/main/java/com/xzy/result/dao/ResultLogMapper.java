package com.xzy.result.dao;

import com.xzy.result.vo.ResultLogVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResultLogMapper extends Mapper<ResultLogVo> {
    List<ResultLogVo> findResultLogByDay(@Param("startDate")String startDate,@Param("endDate")String endDate);
}
