package com.xzy.user;

import com.xzy.enums.UserEnums;
import com.xzy.pojo.ctrl.request.LotteryRq;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserLoginError;
import com.xzy.pojo.user.HmUserRunning;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.*;
import com.xzy.pojo.user.response.MemBalanceInfo;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.pojo.user.response.UserLoginErrorVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserInfo findBaseById(Integer userId);

    List<UserInfo> findList(UserRq search);

    HmUser findById(Integer userId);

    HmUser findByUsername(String username);

    HmUser add(AddUserRq userRq);

    void update(Map<String, Object> map, UserLogin userLogin);

    UserInfo findAddUserInfo(Integer id);

    Integer selCountDownlineTotal(Integer userId);

    boolean checkSecondary(Integer userId, String secondary);

    List<HmUser> selectLower(Integer userId, Boolean disk);

    boolean countOrder(Integer userId);

    void delUserId(Integer userId, UserLogin loginExt, String ip);

    void updateCreditBalance(UpUserBalanceRq balanceRq, UserLogin userLogin);

    void updateRechargeBalance(UpUserBalanceRq balanceRq, UserLogin userLogin);

    void allBalance(UpUserBalanceRq balanceRq, UserLogin userLogin);

    void nullMemBalance(Integer userId, String type, String name);

    Boolean updateJumpType(UserEnums.JumpType jumpType, Integer userId);

    MemBalanceInfo selectTodayInfo(Integer userId);

    void updatePassword(String oldPwd, String newPwd, UserLogin loginExt);

    void updateSecondary(String oldPwd, String newPwd, UserLogin loginExt);

    HmWebSite toStartTime(Integer userId);

    List<LotteryRq> getUserLottery(Integer userId);

    List<HmUserRunning> getUserRunning(userLoginRq userLogin);

    void updateUserIdLottery(Integer id, Boolean isOpenUp);

    void updateGroupIdLottery(Integer groupId, Boolean isOpenUp, Integer userId);

    boolean verifyBalance(AddUserRq userRq);

    void updateStopProfitLoss(StopProfitLossVo stopProfitLossVo);

    StopProfitLossVo getStopProfitLoss(Integer userId);

    void passwordErrorIncrement(String username, String ip);

    void passwordErrorReset(Boolean enabledSon, Integer userId);

    void passwordErrorReset(String username, UserLogin loginExt);

    String selectRelation(String username, Boolean son);

    List<UserLoginErrorVo> selectLoginError();

    List<HmUserLoginError> selectLoginErrorByUser(String username, Integer type);

}
