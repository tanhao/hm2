package com.xzy.result.dao;

import com.xzy.result.vo.HttpsStrVo;
import com.xzy.result.vo.ResultLinkVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResultLinkMapper extends Mapper<ResultLinkVo> {

    Integer installResult(ResultLinkVo resultLink);

    List<HttpsStrVo> selectByHttpsAvg(@Param("startDate")String startDate, @Param("endDate")String endDate);

    List<ResultLinkVo> selectByNew10Result(@Param("link")String link, @Param("lotteryKey")String lotteryKey, @Param("ip")String ip);

    List<ResultLinkVo> selectByNewOneResult(@Param("ip")String ip);

}
