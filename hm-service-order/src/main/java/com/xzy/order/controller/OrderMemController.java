package com.xzy.order.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.order.OrderService;
import com.xzy.pojo.order.request.OrderMemRq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mem")
public class OrderMemController extends BaseController {
    @DubboReference
    private OrderService orderService;

    @RequestMapping("/list")
    public ResponseResult findALl(@RequestBody OrderMemRq search){
        search.setUserId(getLoginId());
        return ResponseData.SUCCESS_LIST(orderService.findByMember(search));
    }

    @GetMapping("/report/weeks")
    public ResponseData twoWeeksReport(Integer lotteryId,String winOrLoserState){
        return ResponseData.SUCCESS(orderService.getTwoWeeksReport(getLoginId(),lotteryId,winOrLoserState));
    }

    @GetMapping("/report/game")
    public ResponseData lotteryReport(@RequestParam("day") String localDate, String winOrLoserState){
        return ResponseData.SUCCESS(orderService.getLotteryReport(getLoginId(),localDate,winOrLoserState));
    }

}
