package com.xzy.result.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.xzy.result.dao.LotteryMapper;
import com.xzy.result.dao.ResultInitMapper;
import com.xzy.result.dao.ResultPlanMapper;
import com.xzy.result.util.DateUtil;
import com.xzy.result.util.LotteryUtils;
import com.xzy.result.util.RoadUtils;
import com.xzy.result.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class LinkApiService {
    @Autowired
    ResultInitMapper resultInitMapper;
    @Autowired
    LotteryMapper lotteryMapper;
    @Autowired
    ResultPlanMapper resultPlanMapper;

    public ResultInit findByGameNo(@PathVariable String lotteryKey, @PathVariable String gameNo) {
        if(StrUtil.isEmpty(lotteryKey) || StrUtil.isEmpty(gameNo)){
            return null;
        }
        ResultInit init = new ResultInit();
        init.setLotteryKey(lotteryKey);
        init.setGameNo(gameNo);
        return resultInitMapper.selectOne(init);
    }

    public List<ResultInit> findByDay(@PathVariable String lotteryKey, @PathVariable String day) {
        if(StrUtil.isEmpty(lotteryKey) || StrUtil.isEmpty(day)){
            return null;
        }
        ResultInit init = new ResultInit();
        init.setLotteryKey(lotteryKey);
        LocalDate localDate=LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long start=localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()/1000;
        long end=localDate.plusDays(1).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli()/1000;
        Example example = new Example(ResultInit.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("lotteryKey", lotteryKey);
        criteria.andBetween("actionTime", start,end);
        example.setOrderByClause("lottery_key,game_no desc");
        return resultInitMapper.selectByExample(example);
    }


    public List<ResultInit> selectPeriodByKey(String lotteryKey,List<Long> list){
        list.removeAll(Collections.singletonList(null));
        if(StrUtil.isEmpty(lotteryKey) || list.size()==0){
            return null;
        }
        return resultInitMapper.selectPeriodByKey(lotteryKey,list);
    }

    public List<ResultInitVo> selectByNewResultInit(){
        return resultInitMapper.selectByNewResultInit();
    }

    public List<LotteryApiVo> selectByNewDayResult(){
        return resultInitMapper.selectByNewDayResult();
    }

    public List<LotteryVo> selectByLottery(){
        return lotteryMapper.selectByAllList();
    }

    public LotteryApiVo selectByNextGame(String lotteryKey){
        List<LotteryApiVo> lotteryApiVos = resultInitMapper.selectByNewDayResult();
        LotteryApiVo lotteryApiVo = null;
        for (LotteryApiVo l : lotteryApiVos){
            if(l.getLotteryKey().equals(lotteryKey)){
                lotteryApiVo = l;
            }
        }
        return lotteryApiVo;
    }

    public List<ResultInit> selectByHisList(String lot,String day){
        if(day.equals("")){
            day = DateUtil.format(new Date(),DateUtil.DATEFORMATDAY);
        }
        return resultInitMapper.selectByHisList(lot,day);
    }

    public List<ResultInit> selectBySumHisList(String lot,Integer num){
        return resultInitMapper.selectBySumHisList(lot,num);
    }

    public JSONObject selectRoadInfo(String lot,String day){
        LotteryVo lotteryVo = new LotteryVo();
        lotteryVo.setLotteryKey(lot);
        lotteryVo = lotteryMapper.selectOne(lotteryVo);
        if(lotteryVo.getLotteryKey().isEmpty()) return null;
        String groupKey = lotteryVo.getGroupId()==100?"carship":lotteryVo.getGroupId()==200?"ssc":lotteryVo.getGroupId()==300?"gdkl10":null;
        if(groupKey.isEmpty()) return null;
        if(day.equals(null)){
            day = DateUtil.format(new Date(),DateUtil.DATEFORMATDAY);
        }
        List list = resultInitMapper.selectByHisList(lot,day);
        if(groupKey.equals(LotteryUtils.GROUP_PK10)){
            return RoadUtils.createPk10RoadInfo(list);
        }else if(groupKey.equals(LotteryUtils.GROUP_SSC)){
            return RoadUtils.createSscRoadInfo(list);
        }else if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
            return RoadUtils.createGDKL10RoadInfo(list);
        }
        return new JSONObject();
    }

    public List<ResultPlanVo> findByPlanByDate(String lotteryKey,String dateStr) {
        return resultPlanMapper.findPlanByDay(lotteryKey,dateStr);
    }

    public List<ResultKillVo> findKillByDay(String lotteryKey,String dateStr) {
        return resultPlanMapper.findKillByDay(lotteryKey,dateStr);
    }

    public int findKillByDayCount(String lotteryKey,String dateStr) {
        return resultPlanMapper.findKillByDayCount(lotteryKey,dateStr);
    }
    public ResultPlanVo findByPlanByLot(String lotteryKey) {
        return resultPlanMapper.findByPlanByLot(lotteryKey);
    }

    public List<LotteryPlanVo> findLotteryPlan() {
        return resultPlanMapper.findLotteryPlan();
    }

}
