package com.xzy.ctrl.controller;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserBuhuoService;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.vo.BuhuoVO;

import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserBuhuoController  extends BaseController {

    @DubboReference
    private UserBuhuoService userBuhuoService;

    @GetMapping("/buhuo")
    public ResponseData getBuhuo() {
        BuhuoVO buhuoVO = userBuhuoService.getBuhuoVO(getPrimaryId());
        return ResponseData.SUCCESS(buhuoVO);
    }

    @PutMapping("/buhuo")
    public ResponseResult updateBuhuo(@RequestBody BuhuoRQ buhuo) {
        buhuo.setUserId(getPrimaryId());
        userBuhuoService.updateBuhuo(buhuo);
        if(buhuo.getErrcode()!=200){
            ExceptionCast.cast(buhuo.getErrmsg());
        }
        return ResponseData.SUCCESS(null);
    }
}
