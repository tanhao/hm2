package com.xzy.user.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.user.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mem")
public class BalMemController extends BaseController {
    @DubboReference
    private UserService userService;

    @GetMapping("/balance")
    public ResponseResult getBalance() {
        return ResponseData.SUCCESS(userService.selectTodayInfo(getPrimaryId()));
    }


}
