package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserLimitService;
import com.xzy.ctrl.dao.UserLimitMapper;
import com.xzy.pojo.user.response.UserLotteryLimitRp;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserLimitServiceImpl implements UserLimitService {

    @Autowired
    private UserLimitMapper userLimitMapper;
    @DubboReference
    private UserService userService;

    public List<UserLotteryLimitRp> getUserLotteryLimit(Integer userId, Integer lotteryId){
        return userLimitMapper.getUserLotteryLimit(userId,lotteryId);
    }

}
