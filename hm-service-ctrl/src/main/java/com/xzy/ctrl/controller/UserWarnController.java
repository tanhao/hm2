package com.xzy.ctrl.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserWarnService;
import com.xzy.pojo.ctrl.vo.WarnVO;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserWarnController extends BaseController {

    @DubboReference
    private UserWarnService userWarnService;

    @GetMapping("/warn")
    public ResponseData getWarnVO() {
        WarnVO warnVO = userWarnService.getWarnVO(getPrimaryId());
        return ResponseData.SUCCESS(warnVO);
    }
}
