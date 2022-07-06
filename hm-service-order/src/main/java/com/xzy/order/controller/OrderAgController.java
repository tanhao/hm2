package com.xzy.order.controller;

import cn.hutool.core.bean.copier.BeanCopier;
import cn.hutool.core.bean.copier.CopyOptions;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.order.OrderService;
import com.xzy.pojo.order.HmOrder;
import com.xzy.pojo.order.request.OrderAgRq;
import com.xzy.pojo.order.request.OrderMemRq;
import com.xzy.pojo.order.request.WinLoseRq;
import com.xzy.pojo.order.response.BhAmtRp;
import com.xzy.pojo.order.response.WinLoseInfo;
import com.xzy.pojo.user.ext.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ag")
@Slf4j
public class OrderAgController extends BaseController {
    @DubboReference
    private OrderService orderService;

    @RequestMapping("/list")
    public ResponseResult findALl(@RequestBody OrderAgRq search) {
        search.setAgentId(getPrimaryId());
        List<HmOrder> order = orderService.findOrderByAgentId(search);
        return ResponseData.SUCCESS_LIST(order);
    }

    @RequestMapping("/time/count")
    public ResponseResult findTimeCount(@RequestBody WinLoseRq search) {
        return ResponseData.SUCCESS(orderService.selectDetailTimeCount(search));
    }


    @RequestMapping("/trans/list")
    public ResponseResult findByMem(@RequestBody OrderMemRq search) {
        search.setAgentId(getPrimaryId());
        List<HmOrder> order = orderService.findByMember(search);
        return ResponseData.SUCCESS_LIST(order);
    }

    @RequestMapping("/id/{orderId}")
    public ResponseResult findId(@PathVariable String orderId) {
        return ResponseData.SUCCESS(orderService.findOrderByOrderId(orderId, getPrimaryId()));
    }

    @RequestMapping("/gameno/{day}/{lotteryId}")
    public ResponseResult gameno(@PathVariable String day, @PathVariable Integer lotteryId) {
        return ResponseData.SUCCESS(orderService.findGameNo(lotteryId, day));
    }

    /**
     * 作废注单
     *
     * @param params
     * @return
     */
    @RequestMapping("/void/order")
    public ResponseResult voidOrder(@RequestBody Map<String, Object> params) {
        UserLogin loginExt = getLoginExt();
        log.info("[账号:{}][ip:{}]作废信息[{}]", loginExt.getUsername(), getIpAddr(), params);
        if (!loginExt.getAdmin()) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        String voidType = (String) params.get("voidType");
        String voidReason = (String) params.get("voidReason");
        if ("id_void".equals(voidType)) {
            ExceptionCast.cast("注单号取消暂时不可用");
            //根据id取消
            List<String> orderIds = (List<String>) params.get("orderIds");
            orderService.voidOrderByOrderId(orderIds, voidReason);
        } else if ("no_void".equals(voidType)) {
            //根据期号取消
            Integer lotteryId = (Integer) params.get("lotteryId");
            Long gameNo = Long.parseLong(params.get("gameNo").toString());
            orderService.voidOrderByUser(lotteryId, gameNo, "", voidReason, voidType);
        } else if ("mem_void".equals(voidType)) {
            //根据用户期号取消
            Integer lotteryId = (Integer) params.get("lotteryId");
            Long gameNo = Long.parseLong(params.get("gameNo").toString());
            String username = params.get("username").toString();
            orderService.voidOrderByUser(lotteryId, gameNo, username, voidReason, voidType);
        }
        return ResponseData.SUCCESS();
    }


    @RequestMapping("/winlose")
    public ResponseResult getWinLose(@RequestBody WinLoseRq winLoseRq) {
        if (winLoseRq.getUserId() == null) {
            winLoseRq.setUserId(getLoginExt().getPrimaryId());
        }
        List<WinLoseInfo> winLoseInfos = orderService.selectWinLose(winLoseRq);
        BhAmtRp bhAmtRp = orderService.selectBhWinLose(winLoseRq);
        Map<String, Object> map = new HashMap<>();
        map.put("winLoseInfos", winLoseInfos);
        map.put("bhAmtRp", bhAmtRp);
        return ResponseData.SUCCESS(map);
    }

    @RequestMapping("/winlose/2")
    public ResponseResult getWinLose2(@RequestBody WinLoseRq winLoseRq) {
        if (winLoseRq.getUserId() == null) {
            winLoseRq.setUserId(getLoginExt().getPrimaryId());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(winLoseRq.getStartTime(), formatter);
        LocalDate endDate = LocalDate.parse(winLoseRq.getEndTime(), formatter);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days < 0) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        List<WinLoseInfo> winLoseInfos = new ArrayList<>();
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(200);
        CountDownLatch latch = new CountDownLatch((int) (days + 1));
        for (int i = 0; i < days + 1; i++) {
            WinLoseRq copy = new WinLoseRq();
            BeanCopier.create(winLoseRq, copy, CopyOptions.create()).copy();
            copy.setStartTime(startDate.plusDays(i).toString());
            copy.setEndTime(startDate.plusDays(i).toString());
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        winLoseInfos.addAll(orderService.selectWinLose(copy));
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        cachedThreadPool.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<Integer, WinLoseInfo> infos = new HashMap<>();
        for (WinLoseInfo info : winLoseInfos) {
            boolean key = infos.containsKey(info.getUserId());
            if (key) {
                WinLoseInfo info1 = infos.get(info.getUserId());
                info.addBigDec(info1);
                infos.put(info.getUserId(), info1);
            } else {
                infos.put(info.getUserId(), info);
            }
        }
        List<WinLoseInfo> resInfo = new ArrayList<>();
        Iterator it = infos.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            resInfo.add(infos.get(key));
        }
        Collections.sort(resInfo, Comparator.comparing(WinLoseInfo::getUsername));
        BhAmtRp bhAmtRp = orderService.selectBhWinLose(winLoseRq);
        Map<String, Object> map = new HashMap<>();
        map.put("winLoseInfos", resInfo);
        map.put("bhAmtRp", bhAmtRp);
        return ResponseData.SUCCESS(map);
    }

    @RequestMapping("/winlose/lottery/2")
    public ResponseResult getWinLoseByLottery2(@RequestBody WinLoseRq winLoseRq) {
        if (winLoseRq.getUserId() == null) {
            winLoseRq.setUserId(getLoginExt().getPrimaryId());
        }
        List<WinLoseInfo> lotterys = orderService.selectWinLoseByLottery(winLoseRq);
        List<WinLoseInfo> bhList = orderService.selectBhWinLoseByLottery(winLoseRq);
        Map<String, Object> map = new HashMap<>();
        map.put("lotterys", lotterys);
        map.put("bhList", bhList);
        return ResponseData.SUCCESS(map);
    }

    @RequestMapping("/winlose/lottery")
    public ResponseResult getWinLoseByLottery(@RequestBody WinLoseRq winLoseRq) {
        if (winLoseRq.getUserId() == null) {
            winLoseRq.setUserId(getLoginExt().getPrimaryId());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(winLoseRq.getStartTime(), formatter);
        LocalDate endDate = LocalDate.parse(winLoseRq.getEndTime(), formatter);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days < 0) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        List<WinLoseInfo> winLoseInfos = new ArrayList<>();
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(200);
        CountDownLatch latch = new CountDownLatch((int) (days + 1));
        for (int i = 0; i < days + 1; i++) {
            WinLoseRq copy = new WinLoseRq();
            BeanCopier.create(winLoseRq, copy, CopyOptions.create()).copy();
            copy.setStartTime(startDate.plusDays(i).toString());
            copy.setEndTime(startDate.plusDays(i).toString());
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        winLoseInfos.addAll(orderService.selectWinLoseByLottery(copy));
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    latch.countDown();
                }
            });
        }
        cachedThreadPool.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, WinLoseInfo> infos = new HashMap<>();
        for (WinLoseInfo info : winLoseInfos) {
            boolean key = infos.containsKey(info.getLotteryId());
            if (key) {
                WinLoseInfo info1 = infos.get(info.getLotteryId());
                info.addBigDec(info1);
                infos.put(info.getLotteryId(), info1);
            } else {
                infos.put(info.getLotteryId(), info);
            }
        }
        List<WinLoseInfo> resInfo = new ArrayList<>();
        Iterator it = infos.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            resInfo.add(infos.get(key));
        }
        List<WinLoseInfo> bhList = orderService.selectBhWinLoseByLottery(winLoseRq);
        Map<String, Object> map = new HashMap<>();
        map.put("lotterys", resInfo);
        map.put("bhList", bhList);
        return ResponseData.SUCCESS(map);
    }

    @RequestMapping("/winlose/kind")
    public ResponseResult getWinLoseByKind(@RequestBody WinLoseRq winLoseRq) {
        if (winLoseRq.getUserId() == null) {
            winLoseRq.setUserId(getLoginExt().getPrimaryId());
        }
        List<WinLoseInfo> kinds = orderService.selectKind(winLoseRq);
        Map<String, Object> map = new HashMap<>();
        map.put("kinds", kinds);
        return ResponseData.SUCCESS(map);
    }

}
