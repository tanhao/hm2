package com.xzy.user.dao;

import com.xzy.pojo.user.HmUserPrePt;
import com.xzy.pojo.user.response.UserActualPt;
import com.xzy.pojo.user.response.UserPrePtRp;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface UserPrePtMapper extends Mapper<HmUserPrePt>, MySqlMapper<HmUserPrePt> {

    List<UserPrePtRp> selectUserPreByUserId(Integer userId);

    List<UserPrePtRp> selectUserMemberPreByUserId(Integer userId);

    List<UserActualPt> selectUserActualPt(Integer userId);

    void updateHmUserPrePt(HmUserPrePt prePt);

    void updateHmUserPresPt(HmUserPrePt prePt);

    void updateUserPresPt(Integer userId);
}
