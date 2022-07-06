package com.xzy.user;

import com.xzy.enums.UserEnums;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserLogin;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.AddUserRq;
import com.xzy.pojo.user.request.UpUserBalanceRq;
import com.xzy.pojo.user.request.UserRq;
import com.xzy.pojo.user.request.userLoginRq;
import com.xzy.pojo.user.response.MemBalanceInfo;
import com.xzy.pojo.user.response.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserLoginService {
    void saveLogin(UserLogin login);

    void saveLogoutLog(UserLogin login);

    List<HmUserLogin> selIPList(userLoginRq userLogin);

    List<HmUserLogin> selUserNameList(userLoginRq userLoginRq);

    List<HmUserLogin> selUserIdList(userLoginRq userLoginRq);
}
