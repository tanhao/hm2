package com.xzy.ctrl.schedules;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzy.ctrl.UserOddsJumpService;
import com.xzy.ctrl.dao.UserJumpMapper;
import com.xzy.ctrl.dao.UserOddsJumpMapper;
import com.xzy.pojo.ctrl.bo.UserOddsJumpBO;
import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.socket.TioSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

@Configuration
@Lazy(false)
@EnableAsync
@EnableScheduling
@Slf4j
public class JumpSchedule {
    @DubboReference
    private UserOddsJumpService userOddsJumpService;
    @DubboReference
    private TioSocketService tioSocketService;

    @Autowired
    private UserOddsJumpMapper userOddsJumpMapper;

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }


    private Integer lastUpdateTime=0;
    //每10秒执行一次
    @Scheduled(fixedDelay = 5000)
    public void excute() {
        Integer nowTime= userOddsJumpMapper.getDBNow();
        //log.info("=========================JumpSchedule=======================2:"+nowTime);
        //lotteryId,userId
        lastUpdateTime=lastUpdateTime==0?nowTime:lastUpdateTime;
        Map<Integer, Map<Integer, List<UserOddsJumpBO>>> mapLottery = userOddsJumpService.listNowOddsJump(lastUpdateTime);
        for ( Map.Entry<Integer, Map<Integer, List<UserOddsJumpBO>>> entry1 : mapLottery.entrySet()) {
            Integer lotteryId=entry1.getKey();
            for ( Map.Entry<Integer, List<UserOddsJumpBO>> entry2 : entry1.getValue().entrySet()) {
                Integer userId=entry2.getKey();
                Integer userLevel=entry2.getValue().get(0).getUserLevel();
                List<UserOddsJumpBO>  listJumps=entry2.getValue();
                JSONArray jsonArray=new JSONArray();
                for(UserOddsJumpBO odds:listJumps){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("oddsId", odds.getOddsId());
                    jsonObject.put("odds", odds.getOdds());
                    jsonArray.add(jsonObject);
                }
                String groupName= StrUtil.format("{}-{}",lotteryId,userId);
                JSONObject retjson = new JSONObject();
                retjson.put("userLevel", userLevel);
                retjson.put("oddss",jsonArray);
                SendInfo sendInfo = new SendInfo("odds_jump", retjson);
                try{
                    tioSocketService.sendToGroup(groupName, sendInfo);
                    log.info("[ {} ] [ odds_jump ] : {}", groupName, sendInfo.getContent());
                }catch (Exception e){
                    log.error("[ {} ] [ odds_jump ] : {}", groupName, sendInfo.getContent());
                }
            }
        }
        lastUpdateTime=nowTime;
    }
}
