package com.xzy.controller;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制器
 */
@Component
public class BaseController {
    @Autowired //防止线程不安全
    public HttpServletRequest request;
    @Autowired //防止线程不安全
    public HttpServletResponse response;
    @Autowired //防止线程不安全
    public HttpSession session;
    @Autowired
    public LoginUtil loginUtil;


    /**
     * 获取登陆人数据
   @return
     */
    public UserLogin getLoginExt() {
        String uid = request.getHeader("uid");
        String loginExt = loginUtil.selectToken(uid);
        UserLogin userLogin = JSON.parseObject(loginExt, UserLogin.class);
        return userLogin;
    }

    /**
     * 获取登陆人id
     * @return
     */
    public Integer getLoginId(){
        return getLoginExt().getUserId();
    }
    public Integer getPrimaryId(){
        return getLoginExt().getPrimaryId();
    }

    public String getIpAddr() {
        String s = request.getHeader("x-original-forwarded-for");
        if (s != null && !"".equals(s.trim())) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            return s.split(",")[0];
        }
        s = request.getHeader("X-Forwarded-For");
        if (s != null && !"".equals(s.trim())) {
            return s.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    protected String getRequestUserAgent() {
        Map map = new HashMap();
        try {
            /*map.put("user-agent",request.getHeader("User-Agent"));*/
            UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
            ua.getBrowser().toString();//Chrome
            ua.getVersion();//14.0.835.163
            ua.getEngine().toString();//Webkit
            ua.getEngineVersion();//535.1
            ua.getOs().toString();//Windows 7
            ua.getPlatform().toString();//Windows
            map.put("browser", ua.getBrowser().toString());
            map.put("os", ua.getOs().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JSONObject.toJSONString(map);//ua.getBrowser().toString()+" "+ua.getOs().toString();;
        }
    }
}
