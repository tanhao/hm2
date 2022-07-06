package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.KindService;
import com.xzy.ctrl.LotteryService;
import com.xzy.ctrl.UserCljpService;
import com.xzy.ctrl.UserLotteryService;
import com.xzy.ctrl.dao.UserCljpMapper;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.request.JumpRQ;
import com.xzy.pojo.ctrl.vo.CljpVO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公告业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class UserCljpServiceImpl implements UserCljpService {

    @Autowired
    private UserCljpMapper userCljpMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private LotteryService lotteryService;
    @DubboReference
    private KindService kindService;
    @DubboReference
    private UserLotteryService userLotteryService;

    @Override
    public CljpVO getCljpVO(Integer userId) {
        List<LotteryVO> listLotterys = userLotteryService.listLotteryVOs(userId);
        Map<Integer, List<KindDO>> mapKinds = kindService.mapKindsOfGroupId();

        LotteryVO tmpLottery=listLotterys.get(0);
        KindDO kind=mapKinds.get(tmpLottery.getGroupId()).get(0);
        Integer lotteryId=tmpLottery.getLotteryId();
        Integer kindId=kind.getKindId();

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

        List<UserCljpDO> listUserKinds=this.listUserCljpDOs(userId,lotteryId,kindId,1);
        CljpVO cljpVO =new CljpVO();
        cljpVO.setKinds(mapKinds);
        cljpVO.setLotterys(listLotterys);
        cljpVO.setKindId(kindId);
        cljpVO.setLotteryId(lotteryId);
        cljpVO.setUserKinds(listUserKinds);
        return cljpVO;
    }

    @Override
    public CljpRQ updateCljp(CljpRQ cljp) {
        if(cljp.getUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        log.info("JSON:"+ JSON.toJSONString(cljp));
        cljp.setJson(JSON.toJSONString(cljp));
        userCljpMapper.updateCljp(cljp);
        return cljp;
    }

    @Override
    public Boolean delCljp(CljpRQ cljp) {
        return userCljpMapper.delCljp(cljp);
    }

    @Override
    public List<UserCljpDO> listUserCljpDOs(Integer userId, Integer lotteryId, Integer kindId,Integer model) {
        return userCljpMapper.listUserCljpDOs(userId,lotteryId,kindId,model);
    }

}
