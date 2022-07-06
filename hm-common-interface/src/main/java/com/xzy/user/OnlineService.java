package com.xzy.user;

import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.ext.OnlineNum;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.UserLogRq;
import com.xzy.pojo.user.request.UserRq;

import java.util.List;

public interface OnlineService {
    List<UserLogRq> getOnlineAll(Integer userId, Boolean admin,String type);

     boolean deleteIdOnline(String id);

     boolean outOnlineAll(Integer userId, Boolean admin,String type);

    public OnlineNum getOnlineNum(Integer userId);
}
