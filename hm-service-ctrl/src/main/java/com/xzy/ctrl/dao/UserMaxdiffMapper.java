package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserMaxdiffDO;
import com.xzy.pojo.ctrl.bo.UserMaxdiffBO;
import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.request.MaxdiffRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMaxdiffMapper {
    List<UserMaxdiffBO> listUserMaxdiffDOs(Integer userId);
    void updateCompanyMaxdiff(MaxdiffRQ maxdiff);
}
