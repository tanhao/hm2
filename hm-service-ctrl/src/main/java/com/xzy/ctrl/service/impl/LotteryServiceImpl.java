package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.LotteryService;
import com.xzy.ctrl.dao.LotteryMapper;
import com.xzy.enums.EOpenModel;
import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class LotteryServiceImpl implements LotteryService {

    private final String LOTTERY = "lottery";

    @Autowired
    private LotteryMapper lotteryMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<LotteryVO> listLotterys() {
        return lotteryMapper.listLotterys();
    }

    @Override
    public List<LotteryVO> selectLotteryByCompanyId(Integer companyId) {
        return lotteryMapper.selectLotteryByCompanyId(companyId);
    }

    @Override
    public Boolean updateDelay(Integer lotteryId, Integer delayOpen, Integer precedeClose, Integer zmPrecedeClose) {
        return lotteryMapper.updateDelay(lotteryId,delayOpen,precedeClose,zmPrecedeClose);
    }

    @Override
    public Boolean updateOrdering(List<LotteryDO> lotterys) {
        return lotteryMapper.updateOrdering(lotterys);
    }

    @Override
    public Boolean updateOpenModel(Integer lotteryId, EOpenModel openModel){
        return lotteryMapper.updateOpenModel(lotteryId,openModel.toString());
    }

}
