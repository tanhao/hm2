package com.xzy.user.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.ext.OnlineNum;
import com.xzy.user.OnlineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("online")
@CrossOrigin
@Slf4j
public class OnlineController extends BaseController {

    @DubboReference
    OnlineService onlineService;

    @GetMapping("/onlineAll/{type}")
    public ResponseResult getAllOnline(@PathVariable String type) {
        return ResponseData.SUCCESS(onlineService.getOnlineAll(getPrimaryId(),getLoginExt().getAdmin(),type));
    }

    @GetMapping("/outOnlineAll/{type}")
    public ResponseData outOnlineAll(@PathVariable String type) {
        return ResponseData.SUCCESS(onlineService.outOnlineAll(getPrimaryId(),getLoginExt().getAdmin(),type));
    }

    @DeleteMapping("/online/{id}")
    public ResponseData deleteIdOnline(@PathVariable String id) {
        return ResponseData.SUCCESS(onlineService.deleteIdOnline(id));
    }

    @GetMapping("/onlineNum")
    public ResponseData getOnlineNum() {
        OnlineNum onlineNum = onlineService.getOnlineNum(getPrimaryId());
        onlineNum.setIsSub(getLoginExt().getEnabledSon());
        return ResponseData.SUCCESS(onlineNum);
    }
}
