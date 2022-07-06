package com.xzy.result.service;


import com.xzy.result.dao.ResultLinkMapper;
import com.xzy.result.util.DateUtil;
import com.xzy.result.vo.HttpsStrVo;
import com.xzy.result.vo.ResultInit;
import com.xzy.result.vo.ResultLinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class ResultLinkService {
    @Autowired
    ResultLinkMapper resultLinkMapper;

    /**
     * 获取结果时间统计
     *
     * @return
     */
    public List<HttpsStrVo> selectByHttpsAvg(String startDate, String endDate) {
        return resultLinkMapper.selectByHttpsAvg(DateUtil.getJ6Date(startDate + " 00:00:00"), DateUtil.getJ6Date(endDate + " 23:59:59"));
    }


    /**
     * 获取结果时间统计
     *
     * @return
     */
    public List<ResultLinkVo> selectByNew10Result(String link, String lotteryKey, String ip) {
        return resultLinkMapper.selectByNew10Result(link, lotteryKey, ip);
    }

    public List<ResultLinkVo> selectByNewOneResult(String ip) {
        return resultLinkMapper.selectByNewOneResult(ip);
    }

    @Async
    public void saveResultLink(String https, String lotteryKey, String period, String result,String createIp) {
        ResultLinkVo resultLink = new ResultLinkVo();
        resultLink.setHttps(https);
        resultLink.setGameNo(period);
        resultLink.setLotteryKey(lotteryKey);
        ResultLinkVo linkVo = resultLinkMapper.selectByPrimaryKey(resultLink);
        if (linkVo == null) {
            resultLink.setResult(result);
            resultLink.setCreateIp(createIp);
            Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            resultLink.setCreateTime(second);
            resultLink.setAccountDay(new Date());
            resultLinkMapper.insertSelective(resultLink);
        }
    }

}
