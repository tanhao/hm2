package com.xzy.user.controller;


import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.UserRq;
import com.xzy.user.UserSonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/son")
@Slf4j
public class UserSonController extends BaseController {
    @DubboReference
    UserSonService userSonService;

    @GetMapping("/findList")
    public ResponseResult findList(UserRq search) {
        return ResponseData.SUCCESS_LIST(userSonService.findList(search, getPrimaryId()));
    }

    @GetMapping("/findById")
    public ResponseResult findById(Integer userId) {
        return ResponseData.SUCCESS(userSonService.findById(userId, getLoginId()));
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody HmUserSon hmUserSon) {
        UserLogin loginExt = getLoginExt();
        log.info("{}修改子账号数据[{}]", loginExt.getUsername(), hmUserSon.toString());
        if(loginExt.getEnabledSon()){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        userSonService.update(hmUserSon, loginExt);
        return ResponseData.SUCCESS();
    }

    @PostMapping("/status")
    public ResponseResult updateStatus(@RequestBody Map<String, String> params) {
        UserLogin loginExt = getLoginExt();
        log.info("{}修改子账号状态[{}]", loginExt.getUsername(), params);
        if(loginExt.getEnabledSon()){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        userSonService.updateStatus(params.get("status"), Integer.valueOf(params.get(("sonId"))), loginExt);
        if (params.get("status").equals("CLOSE")) {
            loginUtil.delTokenById(new String[]{params.get(("sonId"))});
        }
        return ResponseData.SUCCESS();
    }

    @PostMapping("/save")
    public ResponseResult addSon(@RequestBody HmUserSon hmUserSon) {
        UserLogin loginExt = getLoginExt();
        if(loginExt.getEnabledSon()){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        hmUserSon.setPrimaryId(loginExt.getPrimaryId());
        hmUserSon.setCompanyId(loginExt.getCompanyId());
        userSonService.add(hmUserSon, loginExt);
        return ResponseData.SUCCESS();
    }

}
