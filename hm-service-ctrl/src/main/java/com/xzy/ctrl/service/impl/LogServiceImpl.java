package com.xzy.ctrl.service.impl;

import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import com.xzy.ctrl.KindService;
import com.xzy.ctrl.LogService;
import com.xzy.ctrl.PlayService;
import com.xzy.ctrl.UserLotteryService;
import com.xzy.ctrl.dao.KindMapper;
import com.xzy.ctrl.dao.LogMapper;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.PlayDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import com.xzy.pojo.ctrl.request.LogRQ;
import com.xzy.pojo.ctrl.response.LogRP;
import com.xzy.pojo.ctrl.vo.CljpVO;
import com.xzy.pojo.ctrl.vo.LogVO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @DubboReference
    private KindService kindService;
    @DubboReference
    private UserLotteryService userLotteryService;
    @DubboReference
    private PlayService playService;

    @Override
    public LogVO getLogVO(Integer userId) {
        List<LotteryVO> listLotterys = userLotteryService.listLotteryVOs(userId);
        Map<Integer, List<KindDO>> mapKinds = kindService.mapKindsOfGroupId();
        Map<Integer, List<PlayDO>> mapPlays = playService.mapPlaysOfGroupId();

        LotteryVO tmpLottery=listLotterys.get(0);
        KindDO kind=mapKinds.get(tmpLottery.getGroupId()).get(0);
        Integer lotteryId=tmpLottery.getLotteryId();
        Integer kindId=kind.getKindId();
        PlayDO play=mapPlays.get(tmpLottery.getLotteryId()).get(0);
        Integer playId=play.getPlayId();

        mapKinds.values().forEach(kinds -> kinds.forEach(k -> {
            k.setGroupId(null);
            k.setKindKey(null);
            k.setIsCljp(null);
            k.setBuhuoType(null);
            k.setIsBuhuo(null);
            k.setDigit(null);
            k.setOrdering(null);
            k.setQuickSettingId(null);
        }));
        listLotterys.forEach(lottery->{
            lottery.setUserId(null);
            lottery.setLotteryKey(null);
            lottery.setDelayOpen(null);
            lottery.setPrecedeClose(null);
            lottery.setZmPrecedeClose(null);
            lottery.setIsOpenSelf(null);
            lottery.setIsLink(null);
            lottery.setIsReset(null);
            lottery.setIsOpenUp(null);
            lottery.setOrdering(null);
            lottery.setResultSize(null);
            lottery.setCodeNums(null);
            lottery.setGameNo(null);
            lottery.setActionTime(null);
            lottery.setOpenTime(null);
        });

        LogVO logVO =new LogVO();
        logVO.setKinds(mapKinds);
        logVO.setPlays(mapPlays);
        logVO.setLotterys(listLotterys);
        logVO.setKindId(kindId);
        logVO.setLotteryId(lotteryId);
        logVO.setPlayId(playId);
        return logVO;
    }

    @Override
    public LogVO getLogCtrl(LogRQ logRQ) {
        List<LogRP> list=logMapper.getLogCtrl(logRQ);
        Integer total=logMapper.getLogCtrlTotal(logRQ);
        LogVO logVO =new LogVO();
        logVO.setSize(logRQ.getPageSize());
        logVO.setPage(logRQ.getPage());
        logVO.setTotal(total);
        logVO.setLogs(list);
        return logVO;
    }

    @Override
    public LogVO getLogZhuanRegress(LogRQ logRQ) {
        List<LogRP> list=logMapper.getLogZhuanRegress(logRQ);
        Integer total=logMapper.getLogZhuanRegressTotal(logRQ);
        LogVO logVO =new LogVO();
        logVO.setSize(logRQ.getPageSize());
        logVO.setPage(logRQ.getPage());
        logVO.setTotal(total);
        logVO.setLogs(list);
        return logVO;
    }

    @Override
    public LogVO getLogZhuanOdds(LogRQ logRQ) {
        List<LogRP> list=logMapper.getLogZhuanOdds(logRQ);
        Integer total=logMapper.getLogZhuanOddsTotal(logRQ);
        LogVO logVO =new LogVO();
        logVO.setSize(logRQ.getPageSize());
        logVO.setPage(logRQ.getPage());
        logVO.setTotal(total);
        logVO.setLogs(list);
        return logVO;
    }

    @Override
    public LogVO getLogOdds(LogRQ logRQ) {
        List<LogRP> list=logMapper.getLogOdds(logRQ);
        Integer total=logMapper.getLogOddsTotal(logRQ);
        LogVO logVO =new LogVO();
        logVO.setSize(logRQ.getPageSize());
        logVO.setPage(logRQ.getPage());
        logVO.setTotal(total);
        logVO.setLogs(list);
        return logVO;
    }

    @Override
    public List<String> listGameNos(LogRQ logRQ) {
        return logMapper.listGameNos(logRQ);
    }

    @Override
    public LogVO getLogLimit(LogRQ logRQ) {
        List<LogRP> list=logMapper.getLogLimit(logRQ);
        Integer total=logMapper.getLogLimitTotal(logRQ);
        LogVO logVO =new LogVO();
        logVO.setSize(logRQ.getPageSize());
        logVO.setPage(logRQ.getPage());
        logVO.setTotal(total);
        logVO.setLogs(list);
        return logVO;
    }


}
