package com.xzy.result.ctr;

import com.xzy.result.service.LinkApiService;
import com.xzy.result.util.ResultJson;
import com.xzy.result.vo.ResultInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class LinkApiCtr {
    @Autowired
    LinkApiService linkApiService;

    @RequestMapping("/period/{lotteryKey}/{gameNo}")
    public ResultJson findPeriod(@PathVariable String lotteryKey,@PathVariable String gameNo){
        return ResultJson.SUCCESS(linkApiService.findByGameNo(lotteryKey,gameNo));
    }

    @RequestMapping("/periods/{lotteryKey}/{day}")
    public ResultJson findPeriods(@PathVariable String lotteryKey,@PathVariable String day){
        List<ResultInit> byDay = linkApiService.findByDay(lotteryKey, day);
        return ResultJson.SUCCESS(byDay);
    }

    @RequestMapping("/list/period/{lotteryKey}")
    public ResultJson period(@PathVariable String lotteryKey,@RequestParam List<Long> gameNos){
        return ResultJson.SUCCESS(linkApiService.selectPeriodByKey(lotteryKey,gameNos));
    }

    @RequestMapping("/new/result")
    public ResultJson newResult(){
        return ResultJson.SUCCESS(linkApiService.selectByNewResultInit());
    }

    @RequestMapping("/lots")
    public ResultJson lotteryList(){
        return ResultJson.SUCCESS(linkApiService.selectByLottery());
    }

    @RequestMapping("/the/rst")
    public ResultJson maxResult(){
        return ResultJson.SUCCESS(linkApiService.selectByNewDayResult());
    }

    @RequestMapping("/next/{lot}")
    public ResultJson getNextResult(@PathVariable String lot){
        return ResultJson.SUCCESS(linkApiService.selectByNextGame(lot));
    }

    @RequestMapping("/his/{lot}")
    public ResultJson hisList(@PathVariable String lot){
        return ResultJson.SUCCESS(linkApiService.selectByHisList(lot,""));
    }

    @RequestMapping("/his/sum/{lot}/{num}")
    public ResultJson his20List(@PathVariable String lot,@PathVariable Integer num){
        return ResultJson.SUCCESS(linkApiService.selectBySumHisList(lot,num));
    }

    @RequestMapping("/his/{lot}/{day}")
    public ResultJson hisByDayList(@PathVariable String lot,@PathVariable String day){
        return ResultJson.SUCCESS(linkApiService.selectByHisList(lot,day));
    }


    @RequestMapping("/road/{lot}/{day}")
    public ResultJson roadObject(@PathVariable String lot,@PathVariable String day){
        return ResultJson.SUCCESS(linkApiService.selectRoadInfo(lot,day));
    }


    /*
     获取计划
     */
    @RequestMapping("/rplan")
    public ResultJson rplan(){
        return ResultJson.SUCCESS(linkApiService.findLotteryPlan());
    }

    @RequestMapping("/plan/list/{lot}/{day}")
    public ResultJson planList(@PathVariable String lot,@PathVariable String day){
        Map map = new HashMap();
        map.put("planDate",linkApiService.findByPlanByDate(lot,day));
        map.put("killDate",linkApiService.findKillByDay(lot,day));
        map.put("killCount",linkApiService.findKillByDayCount(lot,day));
        return ResultJson.SUCCESS(map);
    }

    @RequestMapping("/plan/new/{lot}")
    public ResultJson planList(@PathVariable String lot){
        return ResultJson.SUCCESS(linkApiService.findByPlanByLot(lot));
    }


}
