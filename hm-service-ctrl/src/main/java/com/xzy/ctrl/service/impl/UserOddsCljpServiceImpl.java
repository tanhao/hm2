package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserOddsCljpService;
import com.xzy.ctrl.UserOddsNowService;
import com.xzy.ctrl.dao.UserOddsCljpMapper;
import com.xzy.ctrl.dao.UserOddsJumpMapper;
import com.xzy.pojo.ctrl.bo.UserOddsCljpBO;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
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
public class UserOddsCljpServiceImpl implements UserOddsCljpService {

    @Autowired
    private UserOddsCljpMapper userOddsCljpMapper;

    @Override
    public Map<Integer, double[]> listNowUserOddsCljp(Integer userId,Integer userLevel, Integer lotteryId ) {
       List<UserOddsCljpBO> userOddss=userOddsCljpMapper.listUserOddsCljpDOs(userId,lotteryId);
        Map<Integer, List<UserOddsCljpBO>> mapOdds=userOddss.stream().collect(Collectors.groupingBy(UserOddsCljpBO::getOddsId));
        Map<Integer, double[]> logic=new HashMap<>();
        for(Map.Entry<Integer,List<UserOddsCljpBO>> entry:mapOdds.entrySet()){
            double[] arrOdds=new double[userLevel+1];
            entry.getValue().stream().forEach(o->arrOdds[o.getUserLevel()]=o.getOdds());
            logic.put(entry.getKey(),arrOdds);
        }
        return logic;
    }

}
