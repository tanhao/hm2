package com.xzy.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xzy.enums.UserEnums;
import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.user.HmUserLogin;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.userLoginRq;
import com.xzy.user.UserLoginService;
import com.xzy.user.dao.IpAddressMapper;
import com.xzy.user.dao.UserLoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Transactional
@Slf4j
@DubboService(interfaceClass = UserLoginService.class)
public class UserLoginImpl implements UserLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;
    @Autowired
    IpAddressMapper ipAddressMapper;


    /**
     * 账号登陆日志
     * @param loginExt
     */
    public void saveLogin(UserLogin loginExt){
        HmUserLogin userLogin =new HmUserLogin();
        userLogin.setUserId(loginExt.getPrimaryId());
        userLogin.setUsername(loginExt.getUsername());
        userLogin.setInfo(loginExt.getHttpHeader());
        userLogin.setCreateTime(new Date());
        userLogin.setCreateIp(loginExt.getLoginIp());
        userLogin.setParams(JSONObject.toJSONString(loginExt));
        userLogin.setLogType(UserEnums.LogType.LOGIN.toString());
        userLogin.setCreateDay(LocalDateTime.now().toLocalDate());
        //log.info("saveLogin <> {}",userLogin);
        userLoginMapper.insertSelective(userLogin);
        IpAddress ip = new IpAddress();
        ip.setIp(loginExt.getLoginIp());
        IpAddress ipAddress = ipAddressMapper.selectOne(ip);
        //log.info("ipAddress <> {}",ipAddress);
        if(ipAddress==null){//添加IP表数据
            try {
                ipAddressMapper.insert(ip);
            }catch (Exception e){}
        }
    }

    /**
     * 账号登出日志
     * @param loginExt
     */
    public void saveLogoutLog(UserLogin loginExt){
        HmUserLogin userLogin =new HmUserLogin();
        userLogin.setUserId(loginExt.getPrimaryId());
        userLogin.setUsername(loginExt.getUsername());
        userLogin.setCreateTime(new Date());
        userLogin.setCreateIp(loginExt.getLoginIp());
        userLogin.setInfo(loginExt.getHttpHeader());
        userLogin.setParams(JSONObject.toJSONString(loginExt));
        userLogin.setLogType(UserEnums.LogType.LOGOUT.toString());
        userLogin.setCreateDay(LocalDateTime.now().toLocalDate());
        //log.info("saveLogoutLog <> {}",userLogin);
        userLoginMapper.insertSelective(userLogin);
    }

    /**
     * 按IP查询登入日志信息
     * @param userLogin
     * @return
     */
    public List<HmUserLogin> selIPList(userLoginRq userLogin){
        PageHelper.startPage(userLogin.getPage(), userLogin.getSize());
        return userLoginMapper.selIPList(userLogin);
    }

    /**
     *  按日期和时间查询登入日志
     * @param userLogin
     * @return
     */
    public List<HmUserLogin> selUserNameList(userLoginRq userLogin) {
        PageHelper.startPage(userLogin.getPage(), userLogin.getSize());
        return userLoginMapper.selUserNameList(userLogin);
    }

    /**
     *  按ID查询登入日志
     * @param userLogin
     * @return
     */
    public List<HmUserLogin> selUserIdList(userLoginRq userLogin) {
        PageHelper.startPage(userLogin.getPage(), userLogin.getSize());
        return userLoginMapper.selUserIdList(userLogin);
    }

}
