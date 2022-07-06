package com.xzy.result.jobs;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.vo.ResultCountVo;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.client.HmResultApi;
import com.xzy.result.dao.HmResultMapper;
import com.xzy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ResultHistory {
    @Autowired
    HmResultApi hmResultApi;
    @Autowired
    HmResultMapper hmResultMapper;
    @DubboReference
    ResultReceiveService resultReceiveService;

    @Scheduled(fixedDelay = 30000)
    public void fixedDelayDaily() {
        String day = DateUtil.getAccountDay().toString();
        List<ResultCountVo> vos = hmResultMapper.selectCountByAccountDay(day);
        vos.forEach(os -> {
            if (os.getCountNull() * 3 > os.getCountTotal()) {
                log.info("[{}-{}]多期没有结果，进行批量查询",os.getLotteryId(),os.getLotteryKey());
                String rs = hmResultApi.findPeriods(os.getLotteryKey(), day);
                this.handPeriods(rs);
            } else if (os.getCountNull() > 0) {
                List<HmResult> results = hmResultMapper.selectNoResultByLottery(os.getLotteryId(), day);
                List<String> keys = results.stream().map(HmResult::getGameNo).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
                if(keys.size()>0) {
                    log.info("[{}-{}]多期没有结果，进行精准查询",os.getLotteryId(),os.getLotteryKey());
                    log.info("查询期号[{}]", CollectionUtil.join(keys,","));
                    String rs = hmResultApi.findPeriodList(os.getLotteryKey(), keys);
                    this.handPeriods(rs);
                }
            }
        });
    }

    public void handPeriods(String rs) {
        JSONObject object = JSON.parseObject(rs);
        if ((Boolean) object.get("success")) {
            JSONArray jsonArray = JSON.parseArray(object.get("data").toString());
            List<ResultGrab> grabs = jsonArray.toJavaList(ResultGrab.class);
            grabs.forEach(grab -> {
                if (StrUtil.isNotBlank(grab.getResult())) {
                    grab.setUpdateBy("socket_job");
                    grab.setAutoPayout(Boolean.FALSE);
                    try {
                        Integer i = resultReceiveService.saveResult(grab);
                        if(i.equals(200)) {
                            resultReceiveService.sendResult(grab);
                            resultReceiveService.spPayoutPrize(grab);
                        }
                    } catch (Exception e) {
                        log.info("赛果保存错误。");
                    }
                }
            });
        }
    }
/*
    @Scheduled(cron = "0 0 7 * * ?",zone = "Asia/Shanghai")
    public void fixedResult() {
        try{
            String results = hmResultApi.findNewResult();
            JSONObject object = JSON.parseObject(results);
            if ((Boolean) object.get("success")) {
                JSONArray jsonArray = JSON.parseArray(object.get("data").toString());
                List<HmResult> re = jsonArray.toJavaList(HmResult.class);
                resultReceiveService.addResult(re);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

}
