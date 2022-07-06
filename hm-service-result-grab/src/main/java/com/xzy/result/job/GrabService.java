package com.xzy.result.job;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;


public interface GrabService {
    //抓数据
    @Async
    void linkData(String linkUrl, String lotteryKey);

    List<Map> linkHistoryData(String linkUrl, String lotteryKey);
}
