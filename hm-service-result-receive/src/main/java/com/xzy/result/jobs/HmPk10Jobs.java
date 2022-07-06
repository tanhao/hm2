package com.xzy.result.jobs;

import com.xzy.enums.EOpenModel;
import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.dao.HmResultMapper;
import com.xzy.utils.NumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class HmPk10Jobs {
    @Autowired
    HmResultMapper hmResultMapper;
    @DubboReference
    ResultReceiveService resultReceiveService;

    @Async
    @Scheduled(cron = "5 4/10 * * * ?")
    public void schedule1() {
        this.execResult();
    }

    @Async
    @Scheduled(cron = "5 9/10 * * * ?")
    public void schedule2() {
        this.execResult();
    }

    public void execResult() {
        Integer lotteryId = 106;
        LotteryDO lotteryDO = hmResultMapper.selectLottery(lotteryId);
        if(lotteryDO ==null|| !lotteryDO.getOpenModel().toString().equals(EOpenModel.AUTO.toString())){
            return;
        }
        HmResult hmResult = hmResultMapper.selectNoResultByZkc(lotteryId);
        if (hmResult != null) {
            List<String> nums = NumUtil.hmpk10Number();
            Random random = new Random();
            int idx = random.nextInt(10);
            String result = nums.get(idx);
            ResultGrab resultGrab = new ResultGrab();
            resultGrab.setResult(result);
            resultGrab.setLotteryKey(lotteryDO.getLotteryKey());
            resultGrab.setGameNo(hmResult.getGameNo());
            resultGrab.setUpdateBy("HmPk10Jobs");
            Integer i = resultReceiveService.saveResult(resultGrab);
            if (i.equals(200)) {
                resultReceiveService.sendResult(resultGrab);
                resultReceiveService.spPayoutPrize(resultGrab);
            }
        }
    }


}
