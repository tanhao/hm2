package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.LogService;
import com.xzy.ctrl.NowOrderService;
import com.xzy.ctrl.UserOddsService;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.request.LogRQ;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import com.xzy.pojo.ctrl.request.OddsCloseRQ;
import com.xzy.pojo.ctrl.request.OddsPlusMinusRQ;
import com.xzy.pojo.ctrl.vo.CljpVO;
import com.xzy.pojo.ctrl.vo.LogVO;
import com.xzy.pojo.ctrl.vo.NowOrderVO;
import com.xzy.pojo.user.ext.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class LogController extends BaseController {

    @DubboReference
    private LogService logService;

    @GetMapping("/log-init")
    public ResponseData getLogInit() {
        LogVO logVO = logService.getLogVO(getPrimaryId());
        return ResponseData.SUCCESS(logVO);
    }

    @GetMapping("/game-nos")
    public ResponseData listGameNos(LogRQ logRQ) {
        logRQ.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(logService.listGameNos(logRQ));
    }

    @GetMapping("/log-ctrl")
    public ResponseData listLogCtrl(LogRQ logRQ) {
        logRQ.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(logService.getLogCtrl(logRQ));
    }

    @GetMapping("/log-zhuang-regress")
    public ResponseData listLogZhuangRegress(LogRQ logRQ) {
        //logRQ.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(logService.getLogZhuanRegress(logRQ));
    }

    @GetMapping("/log-zhuang-odds")
    public ResponseData listLogZhuangOdds(LogRQ logRQ) {
        //logRQ.setUserId(getPrimaryId());

        return ResponseData.SUCCESS(logService.getLogZhuanOdds(logRQ));
    }

    @GetMapping("/log-odds")
    public ResponseData listLogOdds(LogRQ logRQ) {
        //logRQ.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(logService.getLogOdds(logRQ));
    }

    @GetMapping("/log-limit")
    public ResponseData listLogLimit(LogRQ logRQ) {
        //logRQ.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(logService.getLogLimit(logRQ));
    }
}
