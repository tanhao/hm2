package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserOddsCloseService;
import com.xzy.ctrl.UserOddsJumpService;
import com.xzy.ctrl.dao.UserOddsCloseMapper;
import com.xzy.ctrl.dao.UserOddsJumpMapper;
import com.xzy.pojo.ctrl.UserOddsCloseDO;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserOddsCloseServiceImpl implements UserOddsCloseService {

    @Autowired
    private UserOddsCloseMapper userOddsCloseMapper;


    @Override
    public Map<Integer, Boolean> listNowUserOddsClose(Integer userId, Integer lotteryId) {
        List<UserOddsCloseDO>  odds= userOddsCloseMapper.listUserOddsCloseDOs(userId,lotteryId);
        return odds.stream().collect(Collectors.toMap(UserOddsCloseDO::getOddsId,val->val.getIsClose()));
    }
}
