package com.xzy.ctrl.controller;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserJumpService;
import com.xzy.enums.EUserType;
import com.xzy.enums.UserEnums;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.request.JumpRQ;
import com.xzy.pojo.ctrl.vo.JumpVO;

import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.user.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserJumpController extends BaseController {

    @DubboReference
    private UserJumpService userJumpService;
    @DubboReference
    private UserService userService;

    @GetMapping("/jump")
    public ResponseData getJumpVO() {
        JumpVO jumpVO = userJumpService.getJumpVO(getPrimaryId());
        return ResponseData.SUCCESS(jumpVO);
    }

    @PutMapping("/jump")
    public ResponseResult updateJump(@RequestBody JumpRQ jump) {
        jump.setUserId(getPrimaryId());
        userJumpService.updateJump(jump);
        return jump.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

    @PutMapping("/jump-model")
    public ResponseResult updateJumpModel(@RequestBody JumpRQ jump) {
        UserEnums.JumpType jumpType=null;
        try{
            jumpType= UserEnums.JumpType.valueOf(jump.getJumpType());
        }catch (Exception e){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        UserLogin user=getLoginExt();
        if(!EUserType.valueOf(user.getUserLevel()).equals(EUserType.AGENT1)){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        Boolean success= userService.updateJumpType(jumpType,user.getPrimaryId());
        return success?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }
}
