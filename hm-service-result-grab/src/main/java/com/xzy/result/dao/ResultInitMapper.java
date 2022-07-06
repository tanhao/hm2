package com.xzy.result.dao;


import com.xzy.result.vo.LotteryApiVo;
import com.xzy.result.vo.ResultInit;
import com.xzy.result.vo.ResultInitVo;
import com.xzy.result.vo.ResultVo;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResultInitMapper extends Mapper<ResultInit> {

    List<ResultInit> selectPeriodByKey(@Param("lotteryKey") String lotteryKey,@Param("list") List<Long> list);

    List<ResultInitVo> selectByNewResultInit();

    List<ResultInit> selectResultInitByList(@Param("lotteryKey") String lotteryKey,@Param("dateStr") String dateStr);

    List<ResultInit> findByGroupId(@Param("groupId") Integer groupId);

    List<ResultInit> findByNullResult(@Param("lotteryKey")String lotteryKey,@Param("dateStr")String dateStr,@Param("byDate") Integer byDate);

    List<ResultVo> findByNullResultCounts(@Param("startDate")String startDate,@Param("endDate")String endDate);

    List<ResultInit> findByNullResultByLotteryKey(@Param("lotteryKey")String lotteryKey,@Param("startDate")String startDate,@Param("endDate")String endDate);

    void batchUpdateResultInit(@Param("items") List resultInits);

    List<ResultInit> selectByDayResult(@Param("lotteryKey")String lotteryKey, @Param("day")String day);

    List<ResultInit> selectBySumHisList(@Param("lotteryKey")String lotteryKey,@Param("num")Integer num);

    List<LotteryApiVo> selectByNewDayResult();

    List<ResultInit> selectByHisList(@Param("lotteryKey")String lotteryKey, @Param("day")String day);

    @Select("SELECT COUNT(*) FROM t_result_init WHERE lottery_key='keno' AND game_no>#{gameNo}")
    Integer selectFastKeno(String gameNo);

    @Select("{CALL SP_CREATE_KENO_GAMENO(#{fastDayStr,mode=IN,jdbcType=INTEGER}, #{fastGameNo,mode=IN,jdbcType=VARCHAR})}")
    @Options(statementType = StatementType.CALLABLE)
    void splCalculateOrderDaily(@Param("fastGameNo")String fastGameNo,@Param("fastDayStr")String fastDayStr);
}
