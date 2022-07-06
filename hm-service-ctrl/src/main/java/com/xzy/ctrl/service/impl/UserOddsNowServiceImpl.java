package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.*;
import com.xzy.ctrl.dao.UserOddsMapper;
import com.xzy.ctrl.dao.UserOddsNowMapper;
import com.xzy.ctrl.dao.UserRegressMapper;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.GroupDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.QuickSettingDO;
import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserOddsNowBO;
import com.xzy.pojo.ctrl.bo.UserRegressBO;
import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import com.xzy.pojo.ctrl.vo.OddsVO;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
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
public class UserOddsNowServiceImpl implements UserOddsNowService {

    @Autowired
    private UserOddsNowMapper userOddsNowMapper;

    @Override
    public Map<Integer, double[]> listNowUserOddsNow(Integer userId,Integer userLevel, Integer lotteryId,String market) {
       List<UserOddsNowBO> userOddss=userOddsNowMapper.listUserOddsNowDOs(userId,lotteryId,market);
        Map<Integer, List<UserOddsNowBO>> mapOdds=userOddss.stream().collect(Collectors.groupingBy(UserOddsNowBO::getOddsId));
        Map<Integer, double[]> logic=new HashMap<>();
        for(Map.Entry<Integer,List<UserOddsNowBO>> entry:mapOdds.entrySet()){
            double[] arrOdds=new double[userLevel+1];
            entry.getValue().stream().forEach(o->arrOdds[o.getUserLevel()]=o.getOdds());
            logic.put(entry.getKey(),arrOdds);
        }
        return logic;
    }

}
