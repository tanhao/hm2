package com.xzy.user.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.HmUserLogin;
import com.xzy.pojo.user.request.userLoginRq;
import com.xzy.user.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("login")
@CrossOrigin
@Slf4j
public class UserLoginController extends BaseController {

    @DubboReference
    private UserLoginService userLoginService;


    /**
     * 按IP查询
     */
    @GetMapping("/selIPList")
    public ResponseResult selIPList(userLoginRq userLogin) {
        userLogin.setUserId(getLoginExt().getPrimaryId());
        List<HmUserLogin> list = userLoginService.selIPList(userLogin);
        return ResponseData.SUCCESS_LIST(list);
    }

    /**
     * 按日期和用户名查询
     */
    @GetMapping("/selUserNameList")
    public ResponseResult selUserNameList(userLoginRq userLogin) {
        userLogin.setUserId(getLoginExt().getPrimaryId());
        List<HmUserLogin> list = userLoginService.selUserNameList(userLogin);
        return ResponseData.SUCCESS_LIST(list);
    }

    /**
     * 按ID查询
     */
    @GetMapping("/selUserIdList")
    public ResponseResult selUserIdList(userLoginRq userLogin) {
        userLogin.setUserId(getLoginExt().getPrimaryId());
        List<HmUserLogin> list = userLoginService.selUserIdList(userLogin);
        return ResponseData.SUCCESS_LIST(list);
    }


    /**
     * 按ID查询
     */
    @GetMapping("/selUserIdByList")
    public ResponseResult selUserIdByList(userLoginRq userLogin) {
        return ResponseData.SUCCESS_LIST(userLoginService.selUserIdList(userLogin));
    }

}
