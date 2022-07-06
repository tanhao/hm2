package com.xzy.user;

import com.xzy.common.model.response.ResponseResult;
import com.xzy.pojo.user.HmUserPrePt;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.response.UserActualPt;
import com.xzy.pojo.user.response.UserPrePtRp;

import java.util.List;
import java.util.Map;

public interface UserPrePtService {

    List<UserPrePtRp> selectUserPreByUserId(Integer userId);

    List<UserPrePtRp> selectUserMemberPreByUserId(Integer userId);

    void savePrePt(List<HmUserPrePt> savePrePts, Integer parentId);

    Map<String, Object> selectUserActualPt(Integer level, Integer userId);

     void updateLowerPt(List<HmUserPrePt> prePt,Integer userId);

     void updateUserPrePt(Integer userId);

     void updateLowerPrePt(List<HmUserPrePt> prePt,Integer userId);

     void updateUserPresPt(Integer userId);
}
