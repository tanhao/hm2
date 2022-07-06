package com.xzy.result.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.xzy.result.dao.LotteryMapper;
import com.xzy.result.dao.ResultInitMapper;
import com.xzy.result.mq.service.MqService;
import com.xzy.result.util.Constants;
import com.xzy.result.util.DateUtil;
import com.xzy.result.util.RedisUtil;
import com.xzy.result.vo.LotteryVo;
import com.xzy.result.vo.PageUtilVo;
import com.xzy.result.vo.ResultInit;
import com.xzy.result.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
@Slf4j
public class ResultInitService {
    @Autowired
    ResultInitMapper resultInitMapper;
    @Autowired
    ResultLogService resultLogService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ResultLinkService resultLinkService;
    @Autowired
    MqService mqService;
    @Autowired
    LotteryMapper lotteryMapper;

    public List<ResultInit> findByGroupId(Integer id) {
        List<ResultInit> list = resultInitMapper.findByGroupId(id);
        return list;
    }

    public List<ResultInit> findByList(String lotteryKey, String dateStr) {
        List<ResultInit> list = resultInitMapper.selectResultInitByList(lotteryKey, dateStr);
        return list;
    }

    /**
     * 查询没结果数据
     *
     * @return
     */
    public List<ResultInit> findByNullResult(String lotteryKey, String dateStr) {
        LotteryVo vo = new LotteryVo();
        vo.setLotteryKey(lotteryKey);
        vo.setByOpen(1);
        LotteryVo one = lotteryMapper.selectOne(vo);
        if (one != null) {
            return resultInitMapper.findByNullResult(lotteryKey, dateStr,one.getByDate());
        }
        return null;
    }

    /**
     * 统计无结果数量
     *
     * @return
     */
    public List<ResultVo> findByNullResultCounts(String startDate, String endDate) {
        return resultInitMapper.findByNullResultCounts(startDate, endDate);
    }

    /**
     * 获取无结果集合
     *
     * @return
     */
    public List<ResultInit> findByNullResultByLotteryKey(String lotteryKey, String startDate, String endDate) {
        return resultInitMapper.findByNullResultByLotteryKey(lotteryKey, startDate, endDate);
    }


    // 固定声明10个线程
    private ExecutorService executorService = Executors.newFixedThreadPool(20);
    // 使用队列插入数据，后面如果改造成批处理之类的也好执行
    private LinkedBlockingDeque<List> queues = new LinkedBlockingDeque<>();

    /**
     * 批量修改
     *
     * @param resultInits
     */
    public void batchUpdateResultInit(List resultInits) {
        if (resultInits.size() > 0) {
            queues.add(resultInits);
            InsertResultInitThread task = new InsertResultInitThread(resultInitMapper, queues);
            executorService.execute(task);
        }
    }

    /**
     * 添加结果
     */
    @Transactional
    public void updateResultInit(ResultInit resultInit, String user) {
        if (user != null) {
            String vNew = resultInit.getLotteryKey() + "_" + resultInit.getGameNo() + "_" + resultInit.getResult();
            resultLogService.saveResultLog("-", vNew, user);
            resultInitMapper.updateByPrimaryKeySelective(resultInit);
            //MqService.sendMsg(resultInit.getLink(),resultInit.getLotteryKey(),resultInit.getGameNo(),resultInit.getResult());
        }
    }

    /**
     * 获取结果时间统计
     *
     * @return
     */
    public List<ResultInit> selectByDayResult(PageUtilVo pageUtilVo) {
        PageHelper.startPage(pageUtilVo.getPage(), pageUtilVo.getSize());
        String dayDate = DateUtil.getToday();
        return resultInitMapper.selectByDayResult(pageUtilVo.getLotteryKey(), dayDate);
    }

    public synchronized Integer saveInitResult(String https, String lotteryKey, String period, String result) {
        //验证赛果
        String lotteryStr = lotteryKey + ":" + result;
        String codeResult = Constants.lotteryCodeNum(lotteryStr);
        ResultInit link = new ResultInit();
        link.setLotteryKey(lotteryKey);
        link.setGameNo(period);
        ResultInit one = resultInitMapper.selectOne(link);
        if (one != null && StrUtil.isBlank(one.getResult()) && StrUtil.isNotBlank(codeResult)) {
            ResultInit up = new ResultInit();
            up.setId(one.getId());
            up.setLink(https);
            up.setResult(codeResult);
            up.setCreateIp(redisUtil.IPkey);
            Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            up.setSecondTime(second - one.getActionTime());
            Integer i = resultInitMapper.updateByPrimaryKeySelective(up);
            if (i.equals(1)) {
                //推送消息到队列中
//                mqService.sendMsg(https, lotteryKey, period, result);
            }
            return i;
        }
        //插入记录表，有主键约束，不会重复插入
        resultLinkService.saveResultLink(https, lotteryKey, period, result, redisUtil.IPkey);
        return 0;
    }

    /**
     * 检查是否有下期数据
     * @param period
     */
    public void checkFastGameNo(String period,String fastDayStr){
        Integer keno = resultInitMapper.selectFastKeno(period);
        if( keno.equals(0)){
            //下期无keno数据，需要重新生成期号
            resultInitMapper.splCalculateOrderDaily(period,fastDayStr);
            log.info("执行生成keno期号，开始期号[{}],时间[{}]", period, fastDayStr);
        }
    }

}
