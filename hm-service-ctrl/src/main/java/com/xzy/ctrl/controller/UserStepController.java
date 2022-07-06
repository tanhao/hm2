package com.xzy.ctrl.controller;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserStepService;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.StepVO;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserStepController  extends BaseController {

    @DubboReference
    private UserStepService userStepService;

    @GetMapping("/step")
    public ResponseData getStepVO() {
        StepVO stepVO = userStepService.getStepVO(getPrimaryId());
        return ResponseData.SUCCESS(stepVO);
    }

    @PutMapping("/step")
    public ResponseResult updateStep(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        orlf.setUserId(getPrimaryId());
        userStepService.updateStep(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

}
