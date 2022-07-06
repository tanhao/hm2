package com.xzy.user.dao;


import com.xzy.pojo.user.HmUserLogin;
import tk.mybatis.mapper.common.Mapper;
import com.xzy.pojo.user.request.userLoginRq;
import java.util.List;


public interface UserLoginMapper extends Mapper<HmUserLogin> {

    List<HmUserLogin> selIPList(userLoginRq hmUserLogin);

    List<HmUserLogin> selUserNameList(userLoginRq loginLogRq);

    List<HmUserLogin> selUserIdList(userLoginRq loginLogRq);
}
