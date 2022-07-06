package com.xzy.smart.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.smart.service.SmartService;
import com.xzy.smart.vo.SmartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SmartController extends BaseController {
    @Autowired
    SmartService smartService;

    @PostMapping("/plan")
    public ResponseResult addSmartPlan(@RequestBody SmartVo smartVo) {
        UserLogin loginExt = getLoginExt();
        smartVo.setUserId(loginExt.getUserId());
        smartVo.setCompanyId(loginExt.getCompanyId());
        smartService.savePlan(smartVo);
        return ResponseData.SUCCESS();
    }

    @GetMapping("/plan/{lotteryId}")
    public ResponseResult getSmartPlan(@PathVariable Integer lotteryId) {
        Integer loginId = getLoginId();
        return ResponseData.SUCCESS_LIST(smartService.getSmartPlan(loginId, lotteryId));
    }

    @GetMapping("/plan/detail/{planId}")
    public ResponseResult getPlanDetail(@PathVariable Integer planId) {
        Integer loginId = getLoginId();
        return ResponseData.SUCCESS(smartService.getPlanDetail(loginId, planId));
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseResult delPlan(@PathVariable Integer planId) {
        Integer loginId = getLoginId();
        smartService.deletePlan(loginId, planId);
        return ResponseData.SUCCESS();
    }

    @PutMapping("/plan/status/{planId}")
    public ResponseResult upPlanStatus(@PathVariable Integer planId) {
        Integer loginId = getLoginId();
        Boolean planStatus = smartService.upPlanStatus(loginId, planId);
        return ResponseData.SUCCESS(planStatus);
    }

}
