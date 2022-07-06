package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.bo.UserRegressBO;
import com.xzy.pojo.ctrl.request.DiffRegressRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRegressMapper {
    List<UserRegressBO> listHoutongRegress(Integer userId);
    List<UserRegressBO> listCompanyRegress(Integer userId);
    List<UserRegressBO> listAgent1RegressStandard(Integer userId);
    List<UserRegressBO> listAgent1RegressUnstandard(@Param("userId") Integer userId,@Param("parentId") Integer parentId);
    List<UserRegressBO> listAgent1RegressUnstandard2(Integer userId);
    List<UserRegressBO> listAgent2ToAgent10Regress(Integer userId);

    List<UserRegressBO> listZhuanRegress(Integer userId);
    void updateZhuanRegress(OddsRegressLimitFulloddsRQ orlf);


}
