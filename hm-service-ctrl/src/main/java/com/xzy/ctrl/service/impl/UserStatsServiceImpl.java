package com.xzy.ctrl.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserStatsService;
import com.xzy.ctrl.UserZhuanOddsService;
import com.xzy.ctrl.dao.UserOddsMapper;
import com.xzy.ctrl.dao.UserStatsMapper;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.bo.UserStatsBO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanOddsVO;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.socket.TioSocketService;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserStatsServiceImpl  implements UserStatsService {

    @Autowired
    private UserStatsMapper userStatsMapper;
    @DubboReference
    private UserService userService;
    @DubboReference
    TioSocketService tioSocketService;

    @Override
    public List<UserStatsBO> listUserStatsBOs(Integer userId, Integer lotteryId,  String gameNo, String buhuo,String markets) {
        return userStatsMapper.listUserStatsBOs(userId,lotteryId,gameNo,buhuo,markets);
    }

    @Override
    public List<UserStatsBO> listUserStatsBOsGroupByLotteryId(Integer userId, Integer companyId,String buhuo, String markets) {
        return userStatsMapper.listUserStatsBOsGroupByLotteryId(userId,companyId,buhuo,markets);
    }

    @Override
    public Double getTodayWinLose(Integer userId) {
        return userStatsMapper.getTodayWinLose(userId);
    }

}
