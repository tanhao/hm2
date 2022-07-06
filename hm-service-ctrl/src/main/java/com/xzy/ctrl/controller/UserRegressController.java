package com.xzy.ctrl.controller;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserOddsService;
import com.xzy.ctrl.UserRegressService;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.OddsVO;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserRegressController extends BaseController {

    @DubboReference
    private UserRegressService userRegressService;
    @DubboReference
    private UserService userService;
}
