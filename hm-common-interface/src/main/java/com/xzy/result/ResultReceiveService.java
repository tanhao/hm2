package com.xzy.result;

import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.request.ResultRq;
import com.xzy.pojo.result.vo.ResultCountVo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface ResultReceiveService {
    List<HmResult> findListByLotteryIdDay(ResultRq params);

    Integer saveResult(ResultGrab resultGrab);

    void resultCancel(Integer resultId, String updateBy,Integer loginId);

    void resultRePayout(Integer id, String result, String updateBy,Integer loginId);

    List<HmResult> findTotalResult(String accountDay);

    List<ResultCountVo> selectCountByResultDay(String day);

    List<ResultCountVo> selectCountByAccountDay(String day);

    List<HmResult> selectNoResultByLottery(Integer lotteryId, String day);

    List<HmResult> selectNoAccountByLottery(Integer lotteryId, String day);

    void savePayoutResult(Integer resultId,Integer loginId, String updateBy);

    Integer spPayoutPrize(ResultGrab resultGrab);
    @Async
    void sendResult(ResultGrab resultGrab);

    void addResult(List<HmResult> result);

}
