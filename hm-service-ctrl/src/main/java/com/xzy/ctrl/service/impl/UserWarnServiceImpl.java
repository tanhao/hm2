package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.KindService;
import com.xzy.ctrl.UserLotteryService;
import com.xzy.ctrl.UserWarnService;
import com.xzy.ctrl.dao.UserWarnMapper;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.vo.WarnVO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 注单预警业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class UserWarnServiceImpl extends UserKindServiceImpl implements UserWarnService {

    @Autowired
    private UserWarnMapper userWarnMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;
    @DubboReference
    private UserLotteryService userLotteryService;
    @DubboReference
    private KindService kindService;

    private final String USER_WARN = "warn:{}";
    private final String LOCK_USER_WARN = "logck:warn:{}";

    @Override
    public WarnVO getWarnVO(Integer userId) {
        WarnVO warnVO = this.getKindVO(new WarnVO(),userId);
        return warnVO;
    }

    @Override
    public String getRedisKey() {
        return USER_WARN;
    }

    @Override
    public String getLockKey() {
        return LOCK_USER_WARN;
    }

    @Override
    public List<? extends UserKindDO>  listUserKindDO(Integer userId) {
        return userWarnMapper.listUserWarnDOs(userId);
    }
}
