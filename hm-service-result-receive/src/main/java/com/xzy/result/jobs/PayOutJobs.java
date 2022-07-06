package com.xzy.result.jobs;

import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.dao.HmResultMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PayOutJobs {
    @Autowired
    HmResultMapper hmResultMapper;
    @DubboReference
    ResultReceiveService resultReceiveService;

    @Scheduled(fixedDelay = 5000)
    public void fixedDelayDaily() {
        List<HmResult> results = hmResultMapper.selectNoDividend();
        results.forEach(rs -> {
            ResultGrab resultGrab = new ResultGrab();
            resultGrab.setLotteryKey(rs.getLotteryKey());
            resultGrab.setGameNo(rs.getGameNo());
            resultGrab.setResult(rs.getResult());
            resultGrab.setUpdateBy("PayOutJobs");
            resultReceiveService.spPayoutPrize(resultGrab);
        });
    }

}
