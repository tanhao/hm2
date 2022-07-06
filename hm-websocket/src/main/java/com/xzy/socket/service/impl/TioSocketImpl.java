package com.xzy.socket.service.impl;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzy.enums.UserEnums;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.socket.TioSocketService;
import com.xzy.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService
@Service
public class TioSocketImpl implements TioSocketService {
    @Autowired
    private TioWebSocketServerBootstrap bootstrap;
    @Resource
    @Qualifier(value = "LoginUtil")
    private LoginUtil loginUtil;

    public void sendToAll(SendInfo sendInfo) {
        String msg = JSONObject.toJSONString(sendInfo);
        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(msg, CharsetUtil.UTF_8));
    }

    public void sendToUserId(Integer userId, SendInfo sendInfo) {
        String msg = JSONObject.toJSONString(sendInfo);
        Tio.sendToUser(bootstrap.getServerTioConfig(), userId.toString(), WsResponse.fromText(msg, CharsetUtil.UTF_8));
    }

    public void sendToToken(String token, SendInfo sendInfo) {
        String msg = JSONObject.toJSONString(sendInfo);
        Tio.sendToToken(bootstrap.getServerTioConfig(), token, WsResponse.fromText(msg, CharsetUtil.UTF_8));
    }
    public void sendToGroup(String group, SendInfo sendInfo) {
        String msg = JSONObject.toJSONString(sendInfo, SerializerFeature.WriteNonStringKeyAsString);
        Tio.sendToGroup(bootstrap.getServerTioConfig(), group, WsResponse.fromText(msg, CharsetUtil.UTF_8));
    }

    public void sendToSelfAgent(Integer userId, SendInfo sendInfo){
        String msg = JSONObject.toJSONString(sendInfo);
        List list= loginUtil.selectTokenList();
        for (Object item : list) {
            UserLogin user = JSON.parseObject(item.toString(), UserLogin.class);
            String parentPath = user.getParentPath();
            Integer primaryId = user.getPrimaryId();
            boolean status = parentPath.contains(","+userId.toString()+",");
            if (status || (primaryId == userId && user.getUserId() != userId)) {
                if(user.getUserType().equals(UserEnums.Utype.AGENT.toString())){
                    Tio.sendToToken(bootstrap.getServerTioConfig(), user.getUid(), WsResponse.fromText(msg, CharsetUtil.UTF_8));
                }
            }
        }
    }

    public void sendToSelfMember(Integer userId, SendInfo sendInfo){
        String msg = JSONObject.toJSONString(sendInfo);
        List list= loginUtil.selectTokenList();
        for (Object item : list) {
            UserLogin user = JSON.parseObject(item.toString(), UserLogin.class);
            String parentPath = user.getParentPath();
            boolean status = parentPath.contains(","+userId.toString()+",");
            if (status) {
                if(user.getUserType().equals(UserEnums.Utype.MEMBER.toString())){
                    Tio.sendToToken(bootstrap.getServerTioConfig(), user.getUid(), WsResponse.fromText(msg, CharsetUtil.UTF_8));
                }
            }
        }
    }

    public void sendToSelfAll(Integer userId, SendInfo sendInfo){
        String msg = JSONObject.toJSONString(sendInfo);
        List list= loginUtil.selectTokenList();
        for (Object item : list) {
            UserLogin user = JSON.parseObject(item.toString(), UserLogin.class);
            String parentPath = user.getParentPath();
            boolean status = parentPath.contains(","+userId.toString()+",");
            if (status) {
                Tio.sendToToken(bootstrap.getServerTioConfig(), user.getUid(), WsResponse.fromText(msg, CharsetUtil.UTF_8));
            }
        }
    }


}
