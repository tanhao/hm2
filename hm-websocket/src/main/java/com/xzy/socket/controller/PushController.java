package com.xzy.socket.controller;

import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.tio.utils.CodeUtil;
import com.xzy.socket.TioSocketService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.utils.hutool.StrUtil;

@RestController
@RequestMapping("/push")
public class PushController {

    @DubboReference
    private TioSocketService webSocket;

    @GetMapping("/msg")
    public void pushMessage(String msg) {
        if (StrUtil.isEmpty(msg)) {
            msg = "[]";
        }
        SendInfo sendInfo = new SendInfo(CodeUtil.MSG_NOTICE,msg);
        webSocket.sendToAll(sendInfo);
    }
}