package com.xzy.user;

import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.UserRq;

import java.util.List;

public interface UserSonService {
    List<HmUserSon> findAll();

    List<HmUserSon> findList(UserRq searchMap, Integer primaryId);

    HmUserSon findById(Integer userId,Integer primaryId);

    HmUserSon findByUsername(String username);

    void add(HmUserSon hmUserSon,UserLogin loginExt);

    void update(HmUserSon user, UserLogin loginExt);

    int updatePassword(String oldPwd, String newPwd, UserLogin loginExt);

    int updateStatus(String status, Integer sonId, UserLogin loginExt);

    List<String> selectSonIdByPrimary(Integer primaryId);

    void updateByPrimaryKeySelective(HmUserSon upUser);
}
