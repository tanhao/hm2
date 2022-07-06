package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserLotteryService;
import com.xzy.ctrl.dao.LotteryMapper;
import com.xzy.ctrl.dao.UserLotteryMapper;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import com.xzy.pojo.user.HmUser;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 彩种业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class UserLotteryServiceImpl implements UserLotteryService {
    // 后桶ID
    private final Integer GROUP_ID = 1;
    @Autowired
    private LotteryMapper lotteryMapper;
    @Autowired
    private UserLotteryMapper userLotteryMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;
    @DubboReference
    private UserService userService;


    private final String USER_LOTTERY = "lottery:{}";
    private final String LOCK_USER_LOTTERY = "lock:lottery:{}";

    @Override
    public List<LotteryVO> listLotteryVOs(Integer userId) {
        HmUser user=userService.findById(userId);
        EUserType userType=EUserType.valueOf(user.getUserLevel());
        List<LotteryVO> lotterys=null;
        if (EUserType.GROUP.equals(userType)||EUserType.ADMIN.equals(userType)) {
           lotterys=lotteryMapper.listLotterys();
        }else{
           lotterys=userLotteryMapper.listLotterys(user.getCompanyId(),userId);
        }
        return lotterys;
    }

    @Override
    public LotteryVO getLotteryVO(Integer userId, Integer lotteryId) {
        HmUser user=userService.findById(userId);
        EUserType userType=EUserType.valueOf(user.getUserLevel());
        LotteryVO lottery=null;
        if (EUserType.GROUP.equals(userType)||EUserType.ADMIN.equals(userType)) {
            lottery=lotteryMapper.getLottery(lotteryId);
        }else{
            lottery=userLotteryMapper.getLottery(user.getCompanyId(),userId,lotteryId);
        }
        return lottery;
    }

    @Override
    public Boolean updateIsLink(Integer userId, Integer lotteryId, Boolean isLink) {
        return userLotteryMapper.updateIsLink(userId,lotteryId,isLink?1:0);
    }

    @Override
    public Boolean updateIsReset(Integer userId, Integer lotteryId, Boolean isReset) {
        return userLotteryMapper.updateIsReset(userId,lotteryId,isReset?1:0);
    }

    @Override
    public Boolean updateOpenOrClose(Integer userId, Integer lotteryId, Boolean isOpen) {
        return userLotteryMapper.updateOpenOrClose(userId,lotteryId,isOpen?1:0);
    }

    @Override
    public Boolean updateOpenOrCloseForUp(Integer userId, Integer lotteryId, Boolean isOpen) {
        return userLotteryMapper.updateOpenOrCloseForUp(userId,lotteryId,isOpen?1:0);
    }

    @Override
    public Boolean updateOpenOrCloseForUpByGroup(Integer userId, Integer groupId, Boolean isOpen) {
        return userLotteryMapper.updateOpenOrCloseForUpByGroup(userId,groupId,isOpen?1:0);
    }

    @Override
    public Boolean updateDelay(Integer userId, Integer lotteryId, Integer delayOpen, Integer precedeClose, Integer zmPrecedeClose) {
        return userLotteryMapper.updateDelay(userId,lotteryId,delayOpen,precedeClose,zmPrecedeClose);
    }
    @Override
    public Boolean updateDelayObo(Integer userId, Integer lotteryId,String delayType, Integer delay) {
        return userLotteryMapper.updateDelayObo(userId,lotteryId,delayType,delay);
    }


    @Override
    public Boolean updateOrdering(Integer userId,List<LotteryDO> lotterys) {
        return userLotteryMapper.updateOrdering(userId,lotterys);
    }


}
