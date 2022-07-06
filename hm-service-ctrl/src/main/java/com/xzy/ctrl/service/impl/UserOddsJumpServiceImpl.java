package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserOddsJumpService;
import com.xzy.ctrl.UserOddsNowService;
import com.xzy.ctrl.dao.UserOddsJumpMapper;
import com.xzy.ctrl.dao.UserOddsNowMapper;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
import com.xzy.pojo.ctrl.bo.UserOddsNowBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserOddsJumpServiceImpl implements UserOddsJumpService {

    @Autowired
    private UserOddsJumpMapper userOddsJumpMapper;

    @Override
    public Map<Integer, double[]> listNowUserOddsJump(Integer userId,Integer userLevel, Integer lotteryId) {
        List<UserOddsJumpBO> userOddss=userOddsJumpMapper.listUserOddsJumpDOs(userId,lotteryId);
        Map<Integer, List<UserOddsJumpBO>> mapOdds=userOddss.stream().collect(Collectors.groupingBy(UserOddsJumpBO::getOddsId));
        Map<Integer, double[]> logic=new HashMap<>();
        for(Map.Entry<Integer,List<UserOddsJumpBO>> entry:mapOdds.entrySet()){
            double[] arrOdds=new double[userLevel+1];
            entry.getValue().stream().forEach(o->arrOdds[o.getUserLevel()]=o.getOdds());
            logic.put(entry.getKey(),arrOdds);
        }
        return logic;
    }

    @Override
    public  Map<Integer, Map<Integer,List<UserOddsJumpBO>>> listNowOddsJump(Integer updateTime) {
        List<UserOddsJumpBO> userOddss=userOddsJumpMapper.listOddsJumpDOs(updateTime);
        Map<Integer, Map<Integer,List<UserOddsJumpBO>>> mapOdds=userOddss.stream().collect(Collectors.groupingBy(UserOddsJumpBO::getLotteryId,Collectors.groupingBy(UserOddsJumpBO::getUserId)));
        return mapOdds;
    }

}
