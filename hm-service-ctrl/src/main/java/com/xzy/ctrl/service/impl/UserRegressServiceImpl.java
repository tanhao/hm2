package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserRegressService;
import com.xzy.ctrl.dao.UserRegressMapper;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserRegressServiceImpl implements UserRegressService{

    @Autowired
    private UserRegressMapper userRegressMapper;
    @DubboReference
    private UserService userService;
}
