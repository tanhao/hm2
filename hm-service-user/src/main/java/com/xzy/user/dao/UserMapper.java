package com.xzy.user.dao;

import com.xzy.enums.UserEnums;
import com.xzy.pojo.ctrl.dto.UserDTO;
import com.xzy.pojo.ctrl.request.LotteryRq;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserPrePt;
import com.xzy.pojo.user.HmUserRunning;
import com.xzy.pojo.user.HmWebSite;
import com.xzy.pojo.user.request.StopProfitLossVo;
import com.xzy.pojo.user.request.UserRq;
import com.xzy.pojo.user.response.BalanceInfo;
import com.xzy.pojo.user.response.MemBalanceInfo;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.pojo.user.response.UserLoginErrorVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<HmUser> {

    List<UserInfo> selectUserListInfo(UserRq userRq);

    void insertCompnayInfo(UserDTO user);

    void insertAgent1Info(UserDTO user);

    void insertAg2ToAg10Info(UserDTO user);

    @Select("SELECT COUNT(1) from hm_website where site_code=#{securityCode}")
    int getSecurityCode(@Param("securityCode") String securityCode);

    @Update("INSERT INTO hm_website(user_id,site_code) values (#{userId},#{securityCode}) ON DUPLICATE KEY UPDATE  site_code=#{securityCode}")
    int updateSecurityCode(@Param("userId") Integer userId, @Param("securityCode") String securityCode);

    @Update("update hm_website set site_name=#{siteName} where user_id = #{userId}")
    int updateSiteName(@Param("userId") Integer userId, @Param("siteName") String siteName);

    @Select("SELECT * from hm_website where user_id=#{userId}")
    HmWebSite getHmConfigUserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO hm_website(user_id,site_code,site_name,site_corp) value (#{userId},#{siteCode},#{siteName},#{siteCorp})")
    void insertHmWebSiteInfo(HmWebSite webSite);

    void updateUserMarket(Map map);

    Boolean checkMaxUser(@Param("userId") Integer userId);

    void updateBaseGainOdds(@Param("userId") Integer userId, @Param("gainOdds") String gainOdds);

    void updateBaseControlOdds(@Param("userId") Integer userId, @Param("controlOdds") String controlOdds);

    void updateBhStatus(@Param("userId") Integer userId, @Param("bhStatus") boolean bhStatus);

    void updateMarketSelect(@Param("userId") Integer userId, @Param("marketSelect") String marketSelect);

    @Update("update hm_user set company_id=#{companyId} where user_id = #{userId}")
    void updateCompanyId(@Param("companyId") Integer companyId, @Param("userId") Integer userId);

    @Select("SELECT COUNT(1) from hm_user where parent_id=#{userId}")
    Integer selCountDownlineTotal(@Param("userId") Integer userId);

    List<HmUser> selectLower(@Param("userId") Integer userId, @Param("disk") Boolean disk);

    @Select("SELECT COUNT(1) FROM hm_order.hm_order o WHERE user_id in (SELECT user_id FROM hm_user WHERE (parent_path LIKE CONCAT('%,',#{userId},',%') OR user_id =#{userId})  AND user_type='MEMBER')and o.account_day=#{date}")
    int verifyToBet(@Param("userId") Integer userId, @Param("date") LocalDate date);

    @Select("SELECT COUNT(1) FROM hm_order.hm_order o WHERE user_id in (SELECT user_id FROM hm_user WHERE (parent_path LIKE CONCAT('%,',#{userId},',%') OR user_id =#{userId})  AND user_type='MEMBER')and o.account_day=#{date} and o.`status`='WAITING'")
    int verifyToWaitingBet(@Param("userId") Integer userId, @Param("date") LocalDate date);

    int updateUserPreBh(HmUserPrePt hmUserPrePt);

    void updateUserPrePt(Integer userId);

    void delUserId(Integer userId);

    void insertRunning(Map map);

    BalanceInfo selectBalanceInfo(Integer userId);

    @Update("update hm_user set jump_type=#{jumpType} where user_id = #{userId}")
    Boolean updateJumpType(@Param("jumpType") UserEnums.JumpType jumpType, @Param("userId") Integer userId);

    /*全回收余额*/
    void allRecovery(Map map);

    /*清空会员充值额度*/
    void nullMemBalance(Map map);

    MemBalanceInfo selectTodayInfo(@Param("userId") Integer userId, @Param("accountDay") String accountDay);

    @Update("update hm_user set status=#{status} where (parent_path LIKE CONCAT('%,',#{userId},',%')OR user_id =#{userId})")
    void updateStatus(@Param("userId") Integer userId, @Param("status") String status);

    @Select("SELECT * FROM hm_user WHERE (parent_path LIKE CONCAT('%,',#{userId},',%') OR user_id =#{userId}) ")
    List<HmUser> selectParentPath(@Param("userId") Integer userId);


    UserInfo selSwitchStatus(@Param("userId") Integer userId);

    @Select("SELECT ur.*,u.username FROM hm_user_running ur,hm_user u WHERE ur.user_id=u.user_id and ur.create_day = #{day} and ur.category ='use' and u.user_id =#{userId}  ORDER BY create_date_time DESC ")
    List<HmUserRunning> getUserRunning(@Param("userId") Integer userId, @Param("day") String day);

    @Select("select ul.*,l.lottery_key,l.lottery_name,l.group_id,g.group_name from hm_ctrl.t_user_lottery ul,hm_ctrl.t_lottery l,hm_ctrl.t_group g where ul.lottery_id=l.lottery_id and l.group_id=g.group_id and ul.user_id =#{userId}")
    List<LotteryRq> getUserLottery(@Param("userId") Integer userId);

    @Update("update hm_ctrl.t_user_lottery set is_open_self=#{isOpenSelf} where lottery_id in(SELECT lottery_id from hm_ctrl.t_lottery where group_id=#{groupId}) and user_id=#{userId}")
    void updateGroupIdLottery(@Param("groupId") Integer groupId, @Param("isOpenSelf") Boolean isOpenSelf, @Param("userId") Integer userId);

    void updateUserIdLottery(@Param("id") Integer id, @Param("isOpenUp") Boolean isOpenUp);

    void updateStopProfitLoss(@Param("userId") Integer userId, @Param("stopProfit") Integer stopProfit, @Param("stopLoss") Integer stopLoss);

    @Select("SELECT user_id,stop_profit,stop_loss FROM hm_user_base WHERE user_id =#{userId} ")
    StopProfitLossVo getStopProfitLoss(@Param("userId") Integer userId);

    String selectRelationByUser(String username);

    String selectRelationBySon(String username);

    @Select(" SELECT username,STATUS,password_error,count_error,CONCAT(user_type,user_level) lvl,IF(user_type='AGENT','代理','会员') user_type,unlock_count FROM hm_user WHERE count_error>0" +
            " UNION ALL SELECT s.username,s.STATUS,s.password_error,s.count_error,CONCAT(u.user_type,u.user_level) lvl,CONCAT(u.username,'子账号'),s.unlock_count FROM hm_user_son s,hm_user u" +
            " WHERE s.primary_id=u.user_id  AND s.count_error>0" +
            " ORDER BY count_error DESC")
    List<UserLoginErrorVo> selectLoginError();
}
