package com.xzy.ctrl.dao;

import java.util.List;


import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserZhuanOddsBO;
import com.xzy.pojo.ctrl.request.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserOddsMapper {
    List<UserOddsBO> listHoutongOdds(Integer userId);
    List<UserOddsBO> listCompanyOdds(Integer userId);
    List<UserOddsBO> listAgent1OddsStandard(Integer userId);
    List<UserOddsBO> listAgent1OddsUnstandard(Integer userId);
    List<UserOddsBO> listAgent2ToAgent10Odds(Integer userId);
    List<UserZhuanOddsBO> listZhuanOdds(Integer userId);

    void updateORCLHoutong(OddsRegressLimitFulloddsRQ orlf);
    void updateORCLCompany(OddsRegressLimitFulloddsRQ orlf);
    void updateCompanyORLF(OddsRegressLimitFulloddsRQ orlf);
    void updateAgent1ORL(OddsRegressLimitFulloddsRQ orlf);
    void updateAgent1RegressLimit(OddsRegressLimitFulloddsRQ orlf);


    void updateZhuanOdds(OddsRegressLimitFulloddsRQ orlf);

    void copyCompany(CopyORLFRQ copy);
    void copyAgent1(CopyORLFRQ copy);

    List<UserOddsBO> listNowAgent1ToAgent10Odds(@Param("userId")Integer userId, @Param("lotteryId")Integer lotteryId, @Param("market")String market);
    List<UserOddsBO> listNowHouTongOrCompanyOdds(@Param("userId")Integer userId ,@Param("lotteryId")Integer lotteryId,@Param("market")String market);

    void updateUserOdds(OddsPlusMinusRQ pmos);
    Boolean updateUserOddsStatus(OddsCloseRQ oc);


}
