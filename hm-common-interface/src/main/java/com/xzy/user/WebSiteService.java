package com.xzy.user;


import com.alibaba.fastjson.JSONArray;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.pojo.user.ext.UserLogin;

public interface WebSiteService {

    HmWebSite getCompanyName(Integer id);

    HmWebSite selectByPrimaryKey(Integer id);

    void updateTime(HmWebSite hmWebSite, UserLogin login);

    JSONArray selSiteCode(String code, String type);

    HmWebSite selectByCode(String code);
}
