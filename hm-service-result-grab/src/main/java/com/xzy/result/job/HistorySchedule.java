package com.xzy.result.job;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.xzy.result.service.LotteryService;
import com.xzy.result.service.ResultInitService;
import com.xzy.result.util.Constants;
import com.xzy.result.util.DateUtil;
import com.xzy.result.util.ReadJson;
import com.xzy.result.util.RedisUtil;
import com.xzy.result.vo.LotteryVo;
import com.xzy.result.vo.ResultInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
public class HistorySchedule {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private ResultInitService resultInitService;
    @Autowired
    private ApplicationContext applicationContext;

    //今日历史记录搜索每分钟执行一次
    @Scheduled(fixedDelay = 60000)
    public void todayHistory() {
        String dateStr = DateUtil.getToday();
        this.excute(dateStr);
    }

    //昨日历史记录搜索每十分钟执行一次
    @Scheduled(fixedDelay = 100 * 6000)
    public void yestodayHistory() {
        String dateStr = DateUtil.getYestoday();
        this.excute(dateStr);
    }

    public void excute(String dateStr) {
        List<LotteryVo> lotteryVos = lotteryService.findByOpen();
        List<Map> mapHistory = new ArrayList<>();
        for (LotteryVo lottery : lotteryVos) {
            String lotteryKey = lottery.getLotteryKey();
            List<ResultInit> resultInitList = resultInitService.findByNullResult(lotteryKey, dateStr);
            if (resultInitList.size() > 0) {
                JSONObject json168 = ReadJson.getLink168Json();
                if (ObjectUtil.isNotNull(json168.get(lotteryKey))) {
                    JSONObject link168 = JSONObject.parseObject(json168.get(lotteryKey).toString());
                    String url = String.format(link168.get("url").toString() , dateStr);
                    try {
                        GrabService grabService = applicationContext.getBean(Constants.kai_168, GrabService.class);
                        mapHistory = grabService.linkHistoryData(url, lotteryKey);
                    } catch (Exception e) {
                        log.error("出现异常 168历史查询失败：" + e.getMessage(), e);
                    }
                }
                if (mapHistory == null || mapHistory.size() == 0) {
                    //168查询异常转163
                    JSONObject json163 = ReadJson.getLink163Json();
                    if (ObjectUtil.isNotNull(json163.get(lotteryKey))) {
                        JSONObject link163 = JSONObject.parseObject(json163.get(lotteryKey).toString());
                        String url163 = String.format(link163.get("url").toString(), dateStr);
                        try {
                            GrabService grabService = applicationContext.getBean(Constants.kai_163, GrabService.class);
                            mapHistory = grabService.linkHistoryData(url163, lotteryKey);
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            log.error("出现异常 163历史查询失败：" + e1.getMessage(), e1);
                        }
                    }
                }
                if (mapHistory != null && mapHistory.size() > 0) {
                    List<Map> newTransfer = mapHistory.stream().filter(m ->
                            resultInitList.stream().map(e -> e.getLotteryKey() + e.getGameNo()).collect(Collectors.toList())
                                    .contains(m.get("lotteryKey").toString() + m.get("gameNo").toString())).collect(Collectors.toList());
                    if (newTransfer.size() > 0) {
                        getIsResultNullList(lotteryKey, mapHistory, resultInitList);
                    }
                }
            }
        }
    }

    public void getIsResultNullList(String lotteryKey, List<Map> resHistory, List<ResultInit> resultInits) {
        List<Map> newTransfer = resHistory.stream().filter(m ->
                resultInits.stream().map(e -> e.getLotteryKey() + e.getGameNo()).collect(Collectors.toList())
                        .contains(m.get("lotteryKey").toString() + m.get("gameNo").toString())).collect(Collectors.toList());
        log.info(lotteryKey + "：插入历史数据>>>>>>>" + newTransfer.size());
        resultInitService.batchUpdateResultInit(newTransfer);
    }

}
