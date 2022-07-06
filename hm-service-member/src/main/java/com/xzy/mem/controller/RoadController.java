package com.xzy.mem.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.controller.BaseController;
import com.xzy.result.RoadService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/road")
public class RoadController extends BaseController {

    @DubboReference
    RoadService roadService;

    @GetMapping("/getRoad/{lotteryId}")
    public ResponseData getRoadInfo(@PathVariable Integer lotteryId){
        return ResponseData.SUCCESS(roadService.getRoadInfo(lotteryId));
    }
}
