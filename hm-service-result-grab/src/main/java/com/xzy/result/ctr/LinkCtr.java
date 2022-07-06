package com.xzy.result.ctr;


import com.alibaba.fastjson.JSONObject;
import com.xzy.result.job.GrabService;
import com.xzy.result.service.LinkService;
import com.xzy.result.service.LotteryService;
import com.xzy.result.service.ResultInitService;
import com.xzy.result.service.ResultLinkService;
import com.xzy.result.util.*;
import com.xzy.result.vo.PageUtilVo;
import com.xzy.result.vo.ResultInit;
import com.xzy.result.vo.ResultLinkVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/link")
@Slf4j
public class LinkCtr {

    @Autowired
    private LinkService linkService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private ResultLinkService resultLinkService;
    @Autowired
    private ResultInitService resultInitService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 网址抓取网址的key
     *
     * @param
     * @return
     */
    @GetMapping("/list")
    public ResultJson findAllLink() {
        return ResultJson.SUCCESS(linkService.findLinkByAll());
    }

    /**
     * 获取游戏集合
     *
     * @param
     * @return
     */
    @GetMapping("/lotlist")
    public ResultJson findAllLottery() {
        return ResultJson.SUCCESS(lotteryService.findLotteryByAll());
    }

    /**
     * 获取历史数据
     *
     * @param
     * @return
     */
    @PutMapping("/history")
    public ResultJson getResultHistory(@RequestBody Map<String, Object> map) {
        try {
            List<Map> threadMap = new ArrayList<>();
            String https = map.get("https").toString();
            String lotteryKey = map.get("lotteryKey").toString();
            String dateStr = map.get("date").toString();
            String linUrl = String.format(map.get("url").toString(), map.get("date").toString());
            GrabService grabService = applicationContext.getBean(https, GrabService.class);
            if (grabService != null) {
                threadMap = grabService.linkHistoryData(linUrl, lotteryKey);
                if (threadMap.size() > 0) {
                    List<ResultInit> mapHistory = resultInitService.findByNullResult(lotteryKey, dateStr);
                    if (lotteryKey.equals("keno")) {
                        //keno数据查询范围增加
                        JSONObject json = ReadJson.getLinkbclcJson();
                        String url2 = JSONObject.parseObject(json.get(lotteryKey).toString()).get("url2").toString();
                        threadMap.addAll(grabService.linkHistoryData(String.format(url2, dateStr), lotteryKey));
                        Date parase = DateUtil.parase(dateStr, DateUtil.DATEFORMATDAY);
                        Calendar rightNow = Calendar.getInstance();
                        rightNow.setTime(parase);
                        rightNow.add(Calendar.DATE, -1);
                        Date dt1 = rightNow.getTime();
                        mapHistory.addAll(resultInitService.findByNullResult(lotteryKey, DateUtil.format(dt1, DateUtil.DATEFORMATDAY)));
                        //查询jnd28数据
                        String jnd28Key = Constants.lotteryName.JND28.getKey();
                        List<ResultInit> jnd28History = resultInitService.findByNullResult(jnd28Key, dateStr);
                        jnd28History.addAll(resultInitService.findByNullResult(jnd28Key, DateUtil.format(dt1, DateUtil.DATEFORMATDAY)));
                        List<Map> updateResult = new ArrayList<>();
                        for (ResultInit resultInit : jnd28History) {
                            for (Map resultMap : threadMap) {
                                if (resultInit.getGameNo().equals(resultMap.get("gameNo").toString())) {
                                    Map hashMap = new HashMap();
                                    hashMap.put("gameNo", resultMap.get("gameNo"));
                                    hashMap.put("result", resultMap.get("result28"));
                                    hashMap.put("link", resultMap.get("link"));
                                    hashMap.put("lotteryKey", jnd28Key);
                                    updateResult.add(hashMap);
                                }
                            }
                        }
                        if (updateResult.size() > 0) {
                            log.info(jnd28Key + "：插入历史数据>>>>>>>" + updateResult.size());
                            resultInitService.batchUpdateResultInit(updateResult);
                        }
                    }
                    if (threadMap != null && threadMap.size() > 0) {
                        List<Map> newTransfer = threadMap.stream().filter(m ->
                                mapHistory.stream().map(e -> e.getLotteryKey() + e.getGameNo()).collect(Collectors.toList())
                                        .contains(m.get("lotteryKey").toString() + m.get("gameNo").toString())).collect(Collectors.toList());
                        if (newTransfer.size() > 0) {
                            List<Map> updateResult = threadMap.stream().filter(m ->
                                    mapHistory.stream().map(e -> e.getLotteryKey() + e.getGameNo()).collect(Collectors.toList())
                                            .contains(m.get("lotteryKey").toString() + m.get("gameNo").toString())).collect(Collectors.toList());
                            log.info(lotteryKey + "：插入历史数据>>>>>>>" + updateResult.size());
                            resultInitService.batchUpdateResultInit(updateResult);
                        }
                    }
                }
            }
            if (threadMap.size() > 0) {
                return ResultJson.SUCCESSLIST(threadMap);
            }
        } catch (Exception e) {
            return ResultJson.ERROR();
        }
        return ResultJson.ERROR();
    }

    @GetMapping("/result/group/{id}")
    public ResultJson getResultList(@PathVariable("id") Integer id) {
        Map map = new HashMap();
        map.put("newList", resultInitService.findByGroupId(id));
        return ResultJson.SUCCESS(map);
    }

    @GetMapping("/result/list/{lotteryKey}/{dateStr}")
    public ResultJson getResultList(@PathVariable("lotteryKey") String lotteryKey, @PathVariable("dateStr") String dateStr) {
        return ResultJson.SUCCESS(resultInitService.findByList(lotteryKey, dateStr));
    }

    @GetMapping("/https/avg/{startDate}/{endDate}")
    public ResultJson getHttpsAvg(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        Map map = new HashMap();
        map.put("counts", resultInitService.findByNullResultCounts(startDate, endDate));
        map.put("avgList", resultLinkService.selectByHttpsAvg(startDate, endDate));
        return ResultJson.SUCCESS(map);
    }

    @GetMapping("/result/list/{lotteryKey}/{startDate}/{endDate}")
    public ResultJson getResultListNull(@PathVariable("lotteryKey") String lotteryKey, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        Map map = new HashMap();
        map.put("nullList", resultInitService.findByNullResultByLotteryKey(lotteryKey, startDate, endDate));
        return ResultJson.SUCCESS(map);
    }


    @RequestMapping("/day")
    public ResultJson getDayResult(@RequestBody PageUtilVo pageUtilVo) {
        return ResultJson.SUCCESS_LIST(resultInitService.selectByDayResult(pageUtilVo));
    }

    /**
     * 保存手动输入的结果
     *
     * @param
     * @return
     */
    @PutMapping("/result")
    public ResultJson putResult(@RequestBody Map<String, Object> map) {
        try {
            ResultInit resultInit = new ResultInit();
            resultInit.setId(Integer.parseInt(map.get("id").toString()));
            resultInit.setResult(map.get("result").toString());
            resultInit.setLotteryKey(map.get("lotteryKey").toString());
            resultInit.setGameNo(map.get("gameNo").toString());
            resultInit.setLink("manual_save");
            Object user = request.getSession().getAttribute("user");
            resultInitService.updateResultInit(resultInit, user.toString());
//            WebSocket.sendMsg(resultInit.getLink(),resultInit.getLotteryKey(),resultInit.getGameNo(),resultInit.getResult());
        } catch (Exception e) {
            return ResultJson.ERROR();
        }
        return ResultJson.SUCCESS("添加完成");
    }

    @GetMapping("/new/{link}/{lotteryKey}/{ip}")
    public ResultJson getNew10Result(@PathVariable("link") String link, @PathVariable("lotteryKey") String lotteryKey, @PathVariable("ip") String ip) {
        Map map = new HashMap();
        map.put("newList", resultLinkService.selectByNew10Result(link, lotteryKey, ip));
        return ResultJson.SUCCESS(map);
    }

    @GetMapping("latest/results")
    public ResultJson getNewResult() {
        Map map = new HashMap();
        List<Object> listIps = redisUtil.getIps();
        List<ResultLinkVo> linkVos = new ArrayList<>();
        listIps.forEach(v -> {
            String ip = v.toString();
            List<ResultLinkVo> rlv = resultLinkService.selectByNewOneResult(ip);
            linkVos.addAll(rlv);
        });
        map.put("newList", linkVos);
        return ResultJson.SUCCESS(map);
    }
}
