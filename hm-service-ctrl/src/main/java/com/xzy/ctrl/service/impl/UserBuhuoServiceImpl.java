package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserBuhuoService;
import com.xzy.ctrl.dao.UserBuhuoMapper;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.bo.UserBuhuoStatsBO;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.vo.BuhuoVO;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
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
public class UserBuhuoServiceImpl extends UserKindServiceImpl implements UserBuhuoService {
    @Autowired
    private UserBuhuoMapper userBuhuoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private UserService userService;

    private final String USER_BUHUO = "buhuo:{}";
    private final String LOCK_USER_BUHUO = "logck:buhuo:{}";

    @Override
    public BuhuoVO getBuhuoVO(Integer userId) {
        BuhuoVO buhuoVO =this.getKindVO(new BuhuoVO(),userId);
        List<UserBuhuoStatsBO> stats =userBuhuoMapper.listUserBuhuoStats(userId);
        Map<Integer, Map<Integer,UserBuhuoStatsBO>> map=stats.stream().collect(Collectors.groupingBy(UserBuhuoStatsBO::getLotteryId,Collectors.toMap(UserBuhuoStatsBO::getKindId, Function.identity())));
        buhuoVO.setBuhuoStats(map);
        return buhuoVO;
    }

    @Override
    public BuhuoRQ updateBuhuo(BuhuoRQ buhuo) {
        ExceptionCast.castCheck(buhuo.getUserId()==null,CtrlCode.USER_ID_NULL);
        UserInfo user = userService.findBaseById(buhuo.getUserId());
        ExceptionCast.castCheck(!user.getBhStatus(),CommonCode.UNAUTHORISE);
        log.info("JSON:"+ JSON.toJSONString(buhuo));
        buhuo.setJson(JSON.toJSONString(buhuo));
        userBuhuoMapper.updateBuhuo(buhuo);
        return buhuo;
    }

    @Override
    public String getRedisKey() {
        return USER_BUHUO;
    }
    @Override
    public String getLockKey() {
        return LOCK_USER_BUHUO;
    }

    @Override
    public  List<? extends UserKindDO> listUserKindDO(Integer userId) {
        return userBuhuoMapper.listUserBuhuoDOs(userId);
    }
}
