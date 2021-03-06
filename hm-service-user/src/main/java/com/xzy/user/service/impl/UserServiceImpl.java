package com.xzy.user.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.enums.EUserType;
import com.xzy.enums.UserEnums;
import com.xzy.logs.HmLogsService;
import com.xzy.pojo.ctrl.dto.UserDTO;
import com.xzy.pojo.ctrl.request.LotteryRq;
import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.user.*;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.*;
import com.xzy.pojo.user.response.BalanceInfo;
import com.xzy.pojo.user.response.MemBalanceInfo;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.pojo.user.response.UserLoginErrorVo;
import com.xzy.user.UserPrePtService;
import com.xzy.user.UserService;
import com.xzy.user.dao.UserBaseMapper;
import com.xzy.user.dao.UserLoginErrorMapper;
import com.xzy.user.dao.UserMapper;
import com.xzy.user.util.BCryptUtil;
import com.xzy.user.util.UserCk;
import com.xzy.utils.DateUtil;
import com.xzy.utils.LoginUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService(interfaceClass = UserService.class)
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserBaseMapper userBaseMapper;
    @DubboReference
    UserPrePtService userPrePtService;
    @Autowired
    LoginUtil loginUtil;
    @Autowired
    UserSonImpl userSonImpl;
    @DubboReference
    HmLogsService hmLogsService;
    @Autowired
    UserLoginErrorMapper userLoginErrorMapper;

    /**
     * ????????????
     *
     * @param search ????????????
     * @return
     */
    public List<UserInfo> findList(UserRq search) {
        PageHelper.startPage(search.getPage(), search.getSize());
        List<UserInfo> userInfos = userMapper.selectUserListInfo(search);
        if (userInfos.size() <= 40) {
            userInfos.forEach(info -> info.setOnline(loginUtil.selectTokenByUserId(info.getUserId()) != null));
        }
        return userInfos;
    }

    public UserInfo findBaseById(Integer userId) {
        ExceptionCast.castCheck(userId == null, CommonCode.INVALID_PARAM);
        UserRq search = new UserRq();
        search.setUserId(userId);
        List<UserInfo> infos = this.findList(search);
        if (infos.size() == 1)
            return infos.get(0);
        ExceptionCast.cast(UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        return null;
    }

    /**
     * ??????Id??????
     *
     * @param userId
     * @return
     */
    public HmUser findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public HmUser findByUsername(String username) {
        HmUser hmUser = new HmUser();
        hmUser.setUsername(username);
        return userMapper.selectOne(hmUser);
    }

    /**
     * ??????
     *
     * @param userRq
     */
    public HmUser add(AddUserRq userRq) {
        String username = UserCk.checkUserName(userRq.getUsername());
        String password = UserCk.checkPassword(userRq.getPassword());
        HmUser user = this.findByUsername(username);
        ExceptionCast.castCheck(null != user, UcenterCode.UCENTER_USERNAME_EXIST);
        //???????????????????????????
        //TODO
        HmUserSon son = userSonImpl.findByUsername(username);
        ExceptionCast.castCheck(null != son, UcenterCode.UCENTER_USERNAME_EXIST);
        HmUser hmUser = new HmUser();
        hmUser.setUsername(username);
        //?????????????????????
        hmUser.setNickName(UserCk.checkFullName(userRq.getNickName()));
        hmUser.setLoginIpBound(userRq.getLoginIpBound());
        hmUser.setPassword(password);
        hmUser.setCreated(new Date());
        hmUser.setUserType(userRq.getUserType());
        //???????????????????????????
        HmUser superUser = this.findById(userRq.getParentId());
        if (null == superUser) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        hmUser.setStatus(superUser.getStatus());
        hmUser.setUserLevel(superUser.getUserLevel() + 1);
        hmUser.setParentId(superUser.getUserId());
        hmUser.setParentPath(superUser.getParentPath() + superUser.getUserId() + ",");
        hmUser.setParentName(superUser.getUsername());
        if (superUser.getUserLevel() == 1) {
            String secondary = UserCk.checkPassword(userRq.getSecondary());//??????2?????????
            hmUser.setSecondary(secondary);
        } else if (superUser.getUserLevel() == 2) {
            if (hmUser.getUserType().equals("AGENT")) {
                hmUser.setDisk(userRq.getDisk());
            }
        }
        if (hmUser.getUserType().equals("AGENT")) {//??????????????????
            hmUser.setBhStatus(userRq.getBhStatus());
        } else {
            hmUser.setBhStatus(false);
        }
        hmUser.setJumpType(UserEnums.JumpType.XZ);
        //log.info("add hm_user:{}", hmUser);
        //1.??????????????????
        userMapper.insertSelective(hmUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(hmUser.getUserId());
        userDTO.setParentId(hmUser.getParentId());
        //userDTO.setUserType(EUserLevel.valueOf(hmUser.getUserLevel()));
        userDTO.setUserType(EUserType.valueOf(hmUser.getUserLevel()));
        userDTO.setIsStandard(userRq.getDisk());
        String[] reg = userRq.getMarketValue().split(",");
        userDTO.setRegressA(Double.parseDouble(reg[0]));
        userDTO.setRegressB(Double.parseDouble(reg[1]));
        userDTO.setRegressC(Double.parseDouble(reg[2]));
        userDTO.setRegressD(Double.parseDouble(reg[3]));
        if (hmUser.getUserLevel() <= 2) {//???????????????
            userMapper.insertCompnayInfo(userDTO);
        } else if (hmUser.getUserLevel() == 3) {//????????????
            userMapper.insertAgent1Info(userDTO);
        } else {//??????10???????????????
            if (userRq.getCommBack() == 0) {//
                userDTO.setRegressA(0.0);
                userDTO.setRegressB(0.0);
                userDTO.setRegressC(0.0);
                userDTO.setRegressD(0.0);
            } else {
                userDTO.setRegressA(Double.parseDouble(userRq.getCommBack().toString()));
                userDTO.setRegressB(Double.parseDouble(userRq.getCommBack().toString()));
                userDTO.setRegressC(Double.parseDouble(userRq.getCommBack().toString()));
                userDTO.setRegressD(Double.parseDouble(userRq.getCommBack().toString()));
            }
            userMapper.insertAg2ToAg10Info(userDTO);
        }
        log.info("userDTO:{}", userDTO);
        if (userDTO.getErrcode() != 200) {
            ExceptionCast.cast(UcenterCode.UCENTER_USER_ERROR);
        }
        //????????????????????????
        HmUserBase hmUserBase = new HmUserBase();
        //??????base??????
        BeanUtils.copyProperties(userRq, hmUserBase);
        //???????????????????????????id
        hmUserBase.setUserId(hmUser.getUserId());
        hmUserBase.setRentalStartDate(userRq.getRentalStartDate());
        //????????????
        HmUserBase upBase = userBaseMapper.selectByPrimaryKey(hmUser.getParentId());

        if (hmUser.getUserType().equals("MEMBER")) {
            //??????????????????????????????
            Boolean canUse = userMapper.checkMaxUser(superUser.getCompanyId());
            ExceptionCast.castCheck(!canUse, UcenterCode.UCENTER_MAX_USER_ERROR);
        }
        if (hmUser.getUserLevel() >= 5) {
            hmUserBase.setControlOdds("CLOSE");
        }
        if (hmUser.getUserLevel().equals(2)) {
            hmUserBase.setMarketOpen(upBase.getMarketOpen());
            hmUserBase.setMaxUser(userRq.getMaxUser());
        } else {
            hmUserBase.setMaxUser(0);
        }
        hmUserBase.setCreditBalance(BigDecimal.ZERO);
        hmUserBase.setUseCreditBalance(BigDecimal.ZERO);
        hmUserBase.setRechargeBalance(BigDecimal.ZERO);
        hmUserBase.setUseRechargeBalance(BigDecimal.ZERO);
        //????????????,????????????
        String[] markets = hmUserBase.getMarketOpen().split(",");
        if (ArrayUtil.isNotEmpty(markets)) {
            String[] upMarkets = upBase.getMarketOpen().split(",");
            if (ArrayUtil.isEmpty(upMarkets)) {
                ExceptionCast.cast(UcenterCode.UCENTER_MARKET_ERROR);
            }
            for (int i = 0; i < markets.length; i++) {
                if (!ArrayUtil.contains(upMarkets, markets[i])) {
                    ExceptionCast.cast(UcenterCode.UCENTER_MARKET_ERROR);
                }
            }
        }
        log.info("add hm_user_base:{}", hmUserBase);
        //2.??????base??????
        userBaseMapper.insertSelective(hmUserBase);


        log.info("??????user??????");
        //?????????????????????????????????
        if (hmUser.getUserLevel() == 2) {
            userMapper.updateCompanyId(hmUser.getUserId(), hmUser.getUserId());//??????????????????????????????????????????ID
            //?????????????????????
            //todo
            HmWebSite hmConfig = new HmWebSite();
            hmConfig.setUserId(hmUser.getUserId());
            String code = UserCk.checkSecurityCode(userRq.getSecurityCode());
            hmConfig.setSiteCode(code);
            if (StrUtil.isNotBlank((userRq.getSiteName()))) {
                hmConfig.setSiteCorp(Boolean.TRUE);
                hmConfig.setSiteName(userRq.getSiteName());
            } else {
                hmConfig.setSiteCorp(Boolean.FALSE);
            }
            int num = userMapper.getSecurityCode(code);
            if (num >= 1) {
                ExceptionCast.cast(UcenterCode.UCENTER_SECURITY_CODE_EXIST);
            }
            userMapper.insertHmWebSiteInfo(hmConfig);//??????config???

        }
        //??????????????????
        List<HmUserPrePt> savePrePts = userRq.getPrePt();
        savePrePts.stream().forEach(pre -> pre.setUserId(hmUser.getUserId()));
        userPrePtService.savePrePt(savePrePts, superUser.getUserId());
        return hmUser;
    }


    /**
     * ??????????????????
     *
     * @param userBalanceRq ??????
     * @param userLogin     ?????????
     */
    @Synchronized
    public void updateCreditBalance(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        log.info("???????????? updateCreditBalance???{}", userBalanceRq.toString());
        ExceptionCast.castCheck(null == userBalanceRq.getUserId(), CommonCode.INVALID_PARAM);
        this.getCheckEnabledSon(userBalanceRq, userLogin);
        //????????????????????????0
        Boolean checkAmount = userBalanceRq.getAmount().compareTo(BigDecimal.ZERO) <= 0;
        ExceptionCast.castCheck(checkAmount, CommonCode.INVALID_PARAM);
        //????????????????????????
        BalanceInfo balanceInfo = userMapper.selectBalanceInfo(userBalanceRq.getUserId());
        //???????????????????????????
        HmUserBase hmUserBase = userBaseMapper.selectByPrimaryKey(userBalanceRq.getUserId());
        //???????????????????????????
        HmUserBase superUserBase = userBaseMapper.selectByPrimaryKey(this.findById(userBalanceRq.getUserId()).getParentId());
        String type = userBalanceRq.getReserveModel().toString();
        if (UserEnums.AccessOperation.DEPOSIT.toString().equals(userBalanceRq.getOperation())) {
            if (balanceInfo.getUserLevel() > 3) {
                BigDecimal maxUse = balanceInfo.getSuperUseBalance();
                if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_DEPOSIT_NOT);
                }
               /* if(userBalanceRq.getAmount().compareTo(balanceInfo.getBalance().subtract(balanceInfo.getUseBalance())) < 1 ){
                    ExceptionCast.cast("???????????????????????????????????????!");
                }*/
                //????????????????????????
                BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            }
            //????????????????????????
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
        } else if (UserEnums.AccessOperation.RECYCLE.toString().equals(userBalanceRq.getOperation())) {
            //????????????
            //?????????????????????????????????????????????????????????????????????
            BigDecimal maxUse = null;//balanceInfo.getUseBalance().min(balanceInfo.getBalance());
            //????????????????????????
            if (balanceInfo.getUserType().equals("AGENT")) {
                maxUse = balanceInfo.getUseBalance().min(balanceInfo.getBalance());
            } else {
                maxUse = balanceInfo.getBalance();
            }
            if ((balanceInfo.getBalance().subtract(userBalanceRq.getAmount())).compareTo(balanceInfo.getBalance().subtract(balanceInfo.getUseBalance())) == -1) {
                ExceptionCast.cast("???????????????????????????????????????!");
            }
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast(UcenterCode.UCENTER_WITHDRAWAL_NOT);
            }
            //????????????????????????
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            if (balanceInfo.getUserLevel() >= 3) {
                //????????????????????????
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
            }
        }
    }

    public void getCheckEnabledSon(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        if (userLogin.getEnabledSon()) {//??????????????????
            if (null == checkIdByPath(userLogin.getPrimaryId(), userBalanceRq.getUserId())) {
                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }
        } else {
            if (null == checkIdByPath(userLogin.getUserId(), userBalanceRq.getUserId())) {
                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }
        }
    }

    @Synchronized
    public void updateRechargeBalance(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        log.info("???????????????{}", userBalanceRq.toString());
        this.getCheckEnabledSon(userBalanceRq, userLogin);
        ExceptionCast.castCheck(StrUtil.isBlank(userBalanceRq.getOperation()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(null == userBalanceRq.getUserId(), CommonCode.INVALID_PARAM);
        //????????????????????????0
        Boolean checkAmount = userBalanceRq.getAmount().compareTo(BigDecimal.ZERO) <= 0;
        ExceptionCast.castCheck(checkAmount, CommonCode.INVALID_PARAM);
        //????????????????????????
        BalanceInfo balanceInfo = userMapper.selectBalanceInfo(userBalanceRq.getUserId());
        //???????????????????????????
        HmUserBase hmUserBase = userBaseMapper.selectByPrimaryKey(userBalanceRq.getUserId());
        //???????????????????????????
        HmUserBase superUserBase = userBaseMapper.selectByPrimaryKey(this.findById(userBalanceRq.getUserId()).getParentId());
        String type = userBalanceRq.getReserveModel().toString();
        if (UserEnums.AccessOperation.DEPOSIT.toString().equals(userBalanceRq.getOperation())) {
            if (balanceInfo.getUserLevel() > 3) {//????????????????????????
                //?????????????????????????????????????????????????????????????????????????????????
                BigDecimal maxUse = balanceInfo.getSuperUseRecBalance();
                if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_DEPOSIT_NOT);
                }
                //????????????????????????
                BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            }
            //????????????????????????
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
        } else if (UserEnums.AccessOperation.RECYCLE.toString().equals(userBalanceRq.getOperation())) {
            //????????????
            //?????????????????????????????????????????????????????????????????????
            BigDecimal maxUse = balanceInfo.getUseRecBalance().min(balanceInfo.getRecBalance());
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast(UcenterCode.UCENTER_WITHDRAWAL_NOT);
            }
            //????????????????????????
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            if (balanceInfo.getUserLevel() >= 3) {
                //????????????????????????
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
            }
        } else if (UserEnums.AccessOperation.WITHDRAWAL.toString().equals(userBalanceRq.getOperation())) {
            //????????????????????????????????????????????? ???????????????= MAX(??????-???????????????,0)
            BigDecimal maxUse = balanceInfo.getUseRecBalance().subtract(balanceInfo.getRecBalance()).max(BigDecimal.ZERO);
            //BigDecimal maxUse = new BigDecimal(Math.max(balanceInfo.getUseRecBalance().subtract(balanceInfo.getRecBalance()).intValue(),0));//balanceInfo.getUseRecBalance();
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast("????????????");
            }
            //????????????????????????
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            //????????????????????????
            //TODO ?????????????????????
            // this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLoginExt.getUsername(), "");
        }
    }

    /**
     * ??????????????????
     *
     * @param userId
     */
    @Synchronized
    public void nullMemBalance(Integer userId, String type, String name) {
        log.info("?????????????????????{}", userId);
        ExceptionCast.castCheck(null == userId, CommonCode.INVALID_PARAM);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        resultMap.put("reserveModel", type);
        resultMap.put("updateBy", name);
        userMapper.nullMemBalance(resultMap);
    }

    /**
     * ???????????????
     *
     * @param userBalanceRq
     * @param userLogin
     */
    @Synchronized
    public void allBalance(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        log.info("??????????????????{}", userBalanceRq.toString());
        if (null == checkIdByPath(userLogin.getPrimaryId(), userBalanceRq.getUserId())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ExceptionCast.castCheck(null == userBalanceRq.getUserId(), CommonCode.INVALID_PARAM);
        String type = userBalanceRq.getReserveModel().toString();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userBalanceRq.getUserId());
        resultMap.put("reserveModel", type);
        resultMap.put("updateBy", userLogin.getUsername());
        resultMap.put("errcode", 0);
        log.info(resultMap.toString());
        userMapper.allRecovery(resultMap);
        log.info(resultMap.toString());
        Integer code = (Integer) resultMap.get("errcode");
        if (!code.equals(200)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
    }

    /**
     * ??????
     */
    public void update(Map<String, Object> map, UserLogin userLogin) {
        log.info("?????????????????????{}", map);
        //??????????????????????????????ID
        ExceptionCast.castCheck(ObjectUtil.isNull(map.get("userId")), CommonCode.INVALID_PARAM);
        Integer userId = (Integer) map.get("userId");
        HmUser oldUser = userMapper.selectByPrimaryKey(userId);
        if (null == checkIdByPath(userLogin.getPrimaryId(), userId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if (null == oldUser) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //?????????????????????????????????
        Boolean flagUser = false, flagBase = false;
        //?????????????????????
        HmUser hmUser = new HmUser();
        hmUser.setUserId(userId);

        HmUserBase hmUserBase = new HmUserBase();
        hmUserBase.setUserId(userId);
        if (oldUser.getUserLevel() <= 2) {//???????????????????????????????????????
            if (ObjectUtil.isNotNull(map.get("secondary"))) {//??????????????????
                flagUser = true;
                String secondary = UserCk.checkPassword(map.get("secondary").toString());
                hmUser.setSecondary(secondary);
            }
        }

        //????????????
        if (ObjectUtil.isNotNull(map.get("password"))) {
            flagUser = true;
            //????????????
            String password = UserCk.checkPassword(map.get("password").toString());
            hmUser.setResetPassword(1);
            hmUser.setPassword(password);
            //???????????????????????????
            loginUtil.delTokenById(new String[]{hmUser.getUserId().toString()});
        }
        //????????????
        if (ObjectUtil.isNotNull(map.get("nickName"))) {
            flagUser = true;
            hmUser.setNickName(UserCk.checkFullName(map.get("nickName").toString()));
        }
        //??????????????????IP
        if (ObjectUtil.isNotNull(map.get("loginIpBound"))) {
            flagUser = true;
            hmUser.setLoginIpBound(map.get("loginIpBound").toString());
        }
        //????????????
        if (ObjectUtil.isNotNull(map.get("rental"))) {
            flagBase = true;
            hmUserBase.setRental(new BigDecimal(map.get("rental").toString()));
        }
        //??????????????????
        if (ObjectUtil.isNotNull(map.get("maxWin"))) {
            flagBase = true;
            hmUserBase.setMaxWin(Integer.valueOf(map.get("maxWin").toString()));
        }
        if (ObjectUtil.isNotNull(map.get("marketSelect"))) {//????????????????????????
            flagBase = true;
            hmUserBase.setMarketSelect((String) map.get("marketSelect"));
        }
        if (ObjectUtil.isNotNull(map.get("maxUser"))) {//????????????????????????
            flagBase = true;
            hmUserBase.setMaxUser((Integer) map.get("maxUser"));
        }
        //?????????????????? ????????????
        if (ObjectUtil.isNotNull(map.get("marketOpen"))) {//??????????????????
            flagBase = true;
            hmUserBase.setMarketOpen((String) map.get("marketOpen"));
        }
        //??????????????????
        if (ObjectUtil.isNotNull(map.get("status"))) {
            String status = (String) map.get("status");
            try {
                UserEnums.Status.valueOf(status);
            } catch (IllegalArgumentException e) {
                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }
            if (status.equals("OPEN")) {
                HmUser parentUser = userMapper.selectByPrimaryKey(oldUser.getParentId());
                if (!parentUser.getStatus().equals("OPEN")) {
                    ExceptionCast.cast(CommonCode.ACCOUNT_CLOSED);
                }
                flagUser = true;
                hmUser.setStatus(status);
            } else {//???????????????????????????????????????????????????
                userMapper.updateStatus(userId, status);
                List<HmUser> user = userMapper.selectParentPath(userId);
                HmUserBase ub = userBaseMapper.selectByPrimaryKey(userId);
                HmUser hu = this.findById(userId);
                if (status.equals("CLOSE")) {
                    if (hu.getUserType().equals("MEMBER")) {//??????????????????
                        this.nullMemBalance(hu.getUserId(), ub.getReserveModel(), userLogin.getNickName());
                    } else {//???????????? ?????????
                        UpUserBalanceRq ubr = new UpUserBalanceRq();
                        ubr.setUserId(hu.getUserId());
                        ubr.setReserveModel(UserEnums.ReserveModel.valueOf(ub.getReserveModel()));
                        this.allBalance(ubr, userLogin);
                    }
                }
                for (HmUser item : user) {
                    loginUtil.delTokenById(new String[]{item.getUserId().toString()});
                    List<String> integers = userSonImpl.selectSonIdByPrimary(item.getUserId());
                    if (integers.size() > 0) {
                        loginUtil.delTokenById(integers.toArray(new String[integers.size()]));
                    }
                }
            }


        }
        if (oldUser.getUserLevel() == 2) { //?????????????????????
            if (ObjectUtil.isNotNull(map.get("disk"))) {
                if (oldUser.getUserType().equals("AGENT")) {
                    hmUser.setDisk((boolean) map.get("disk"));
                }
            }
            if (ObjectUtil.isNotNull(map.get("securityCode"))) {
                String code = UserCk.checkSecurityCode(map.get("securityCode").toString());
                int num = userMapper.getSecurityCode(code);//??????????????????
                if (num >= 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_SECURITY_CODE_EXIST);
                }
                userMapper.updateSecurityCode(hmUser.getUserId(), code);
            }
            if (ObjectUtil.isNotNull(map.get("siteName"))) {
                HmWebSite webSite = userMapper.getHmConfigUserId(userId);//??????????????????????????? ???????????????
                String siteName = map.get("siteName").toString();
                if (webSite != null) {
                    userMapper.updateSiteName(hmUser.getUserId(), siteName);
                } else {
                    HmWebSite web = new HmWebSite();
                    web.setUserId(userId);
                    web.setSiteName(siteName);
                    userMapper.insertHmWebSiteInfo(web);//??????config???
                }
            }
        }
        //????????????
        if (ObjectUtil.isNotNull(map.get("gainOdds"))) {
            String gainOdds = map.get("gainOdds").toString();
            UserInfo userInfo = userMapper.selSwitchStatus(userId);
            if (gainOdds.equals(userInfo.getGainOdds())) {
                ExceptionCast.cast(CommonCode.SUPERIOR_UPDATE);
            }
            //??????????????????????????????
            userMapper.updateBaseGainOdds(userId, gainOdds);
        }
        //????????????
        if (ObjectUtil.isNotNull(map.get("controlOdds"))) {
            String controlOdds = map.get("controlOdds").toString();
            UserInfo userInfo = userMapper.selSwitchStatus(userId);
            if (controlOdds.equals(userInfo.getControlOdds())) {
                ExceptionCast.cast(CommonCode.SUPERIOR_UPDATE);
            }
            //??????????????????????????????
            userMapper.updateBaseControlOdds(userId, controlOdds);
        }
        //??????????????????
        if (ObjectUtil.isNotNull(map.get("bhStatus"))) {
            if (oldUser.getUserType().equals("AGENT")) {
                boolean bhStatus = (boolean) map.get("bhStatus");
                UserInfo userInfo = userMapper.selSwitchStatus(userId);
                if (bhStatus == userInfo.getBhStatus()) {
                    ExceptionCast.cast(CommonCode.SUPERIOR_UPDATE);
                }
                userMapper.updateBhStatus(userId, bhStatus);
            }
        }
        if (flagUser) {
            //?????????????????????
            userMapper.updateByPrimaryKeySelective(hmUser);
        }
        if (flagBase) {
            //???????????????????????????
            userBaseMapper.updateByPrimaryKeySelective(hmUserBase);
        }
    }


    //??????id????????????????????????
    public UserInfo findAddUserInfo(Integer userId) {
        //?????????????????????
        ExceptionCast.castCheck(null == userId, CommonCode.INVALID_PARAM);
        UserRq userListRq = new UserRq();
        userListRq.setUserId(userId);
        List<UserInfo> list = userMapper.selectUserListInfo(userListRq);
        if (list.size() == 1) {
            UserInfo listInfo = list.get(0);
           /* HmUserPrePt prePt = hmUserPrePtService.selectByPrimaryKey(userId);
            if (ObjectUtil.isNull(prePt)) {
                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }*/
            //listInfo.setsetPrePt(prePt);
            if (listInfo.getUserLevel() == 2) {
                HmWebSite hmWebSite = userMapper.getHmConfigUserId(userId);
                if (null != hmWebSite) {
                    listInfo.setSecurityCode(hmWebSite.getSiteCode());
                    listInfo.setSiteName(hmWebSite.getSiteName());
                }
            }
            if (listInfo.getUserType().equals("MEMBER")) {
                listInfo.setBetSlip(this.countOrder(listInfo.getUserId()));
                listInfo.setBetWaiting(this.countOrderWaiting(listInfo.getUserId()));
            }
            return listInfo;
        }
        ExceptionCast.cast(CommonCode.INVALID_PARAM);
        return null;
    }

    /**
     * ??????????????????
     *
     * @param userId
     * @return
     */
    public Integer selCountDownlineTotal(Integer userId) {
        return userMapper.selCountDownlineTotal(userId);
    }

    public boolean checkSecondary(Integer userId, String secondary) {
        HmUser hmUser = userMapper.selectByPrimaryKey(userId);//??????
        return BCryptUtil.matches(secondary, hmUser.getSecondary());
    }

    /**
     * ???????????????
     *
     * @param userId
     * @return
     */
    public boolean countOrder(Integer userId) {
        Integer num = userMapper.verifyToBet(userId, DateUtil.getAccountDay());
        if (num >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ???????????????
     *
     * @param userId
     * @return
     */
    public boolean countOrderWaiting(Integer userId) {
        Integer num = userMapper.verifyToWaitingBet(userId, DateUtil.getAccountDay());
        if (num >= 1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * ??????????????????
     *
     * @param userId
     * @param disk
     * @return
     */
    public List<HmUser> selectLower(Integer userId, Boolean disk) {
        return userMapper.selectLower(userId, disk);
    }

    /**
     * ???????????????????????????
     *
     * @param userId
     * @param loginExt
     */
    public void delUserId(Integer userId, UserLogin loginExt, String ip) {
        HmUser upHmUser = this.checkIdByPath(loginExt.getUserId(), userId);
        if (null != upHmUser) {
            log.info("{}????????????[{}]", loginExt.getUsername(), upHmUser.getUsername());
            userMapper.delUserId(userId);
            loginUtil.delTokenById(new String[]{userId.toString()});
            HmLogs logs = new HmLogs();
            logs.setCreateUser(loginExt.getUsername());//?????????
            logs.setCreateId(loginExt.getUserId().toString());//?????????ID
            logs.setUserId(userId);
            logs.setCreateIp(ip);//IP
            logs.setCreateTime(new Date());//????????????
            logs.setCreateType("????????????");
            logs.setNewValue(upHmUser.getUsername());
            logs.setOldValue(upHmUser.getUsername());
            hmLogsService.addHmLogs(logs);
        }
    }

    /***
     * ????????????
     * @param userId
     * @param category ??????
     * @param operation ????????????
     * @param type     ????????????/????????????
     * @param amount    ??????
     * @param createBy  ?????????
     * @param refId
     */
    public void insertRunning(Integer userId, String category, String operation, String type, BigDecimal amount, String createBy, String refId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("p_user_id", userId);
        resultMap.put("p_category", category);
        resultMap.put("p_operation", operation);
        resultMap.put("p_type", type);
        resultMap.put("p_amount", amount);
        resultMap.put("p_create_by", createBy);
        resultMap.put("p_ref_id", refId);
        resultMap.put("errcode", 0);
        resultMap.put("errmsg", "");
        userMapper.insertRunning(resultMap);
        log.info(resultMap.toString());
        Integer code = (Integer) resultMap.get("errcode");
        if (!code.equals(200)) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
    }


    /**
     * ??????id???????????????id?????????
     *
     * @param loginId
     * @param userId
     * @return
     */
    public HmUser checkIdByPath(Integer loginId, Integer userId) {
        Example example = new Example(HmUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andLike("parentPath", "%," + loginId + ",%");
        return userMapper.selectOneByExample(example);
    }


    public Boolean updateJumpType(UserEnums.JumpType jumpType, Integer userId) {
        return userMapper.updateJumpType(jumpType, userId);
    }

    //????????????????????????
    public MemBalanceInfo selectTodayInfo(Integer loginId) {
        MemBalanceInfo balanceInfo = userMapper.selectTodayInfo(loginId, DateUtil.getAccountDay().toString());
        return balanceInfo;
    }

    /**
     * ??????????????????
     *
     * @param oldPwd
     * @param newPwd
     */
    public void updatePassword(String oldPwd, String newPwd, UserLogin loginExt) {
        //???????????????==?????????
        ExceptionCast.castCheck(StrUtil.equals(oldPwd, newPwd), UcenterCode.UCENTER_NO_PWD);
        //??????????????????
        HmUser hmUser = userMapper.selectByPrimaryKey(loginExt.getUserId());
        //???????????????
        boolean matches = BCryptUtil.matches(oldPwd, hmUser.getPassword());
        ExceptionCast.castCheck(!matches, UcenterCode.UCENTER_OLDPASSWORD_ERROR);
        String password = UserCk.checkPassword(newPwd);
        HmUser user = new HmUser();
        user.setUserId(loginExt.getUserId());
        user.setPassword(password);
        user.setUpdated(new Date());
        user.setResetPassword(0);
        log.info(hmUser.getUsername() + "??????????????????:" + oldPwd + "=>" + newPwd);
        userMapper.updateByPrimaryKeySelective(user);
        this.addUserLog("******", "******", "??????????????????", loginExt.getUserId(), loginExt);
    }

    /**
     * ????????????????????????
     */
    public void addUserLog(String oldValue, String newValue, String type, Integer updateId, UserLogin userLogin) {
        try {
            HmLogs logs = new HmLogs();
            logs.setCreateUser(userLogin.getUsername());//?????????
            logs.setCreateId(userLogin.getUserId().toString());//?????????ID
            logs.setOldValue(oldValue);
            logs.setUserId(updateId);
            logs.setCreateIp(userLogin.getLoginIp());//IP
            logs.setCreateTime(new Date());//????????????
            logs.setCreateType(type);
            logs.setNewValue(newValue);
            hmLogsService.addHmLogs(logs);
        } catch (Exception e) {
            log.info("??????????????????????????????", e);
        }
    }

    /**
     * ??????????????????
     *
     * @param oldPwd
     * @param newPwd
     * @param loginExt
     */
    public void updateSecondary(String oldPwd, String newPwd, UserLogin loginExt) {
        String password = UserCk.checkPassword(newPwd);
        HmUser hmUser = userMapper.selectByPrimaryKey(loginExt.getPrimaryId());
        boolean matches = BCryptUtil.matches(oldPwd, hmUser.getSecondary());
        if (!matches) {
            ExceptionCast.cast(UcenterCode.UCENTER_OLDPASSWORD_ERROR);
        }
        boolean pwd = BCryptUtil.matches(newPwd, hmUser.getSecondary());
        if (pwd) {
            ExceptionCast.cast(UcenterCode.UCENTER_NO_PWD);
        }
        HmUser user = new HmUser();
        user.setUserId(loginExt.getPrimaryId());
        user.setSecondary(password);
        user.setUpdated(new Date());
        log.info(hmUser.getUsername() + " update secondary:" + oldPwd + "=>" + newPwd);
        userMapper.updateByPrimaryKeySelective(user);
        this.addUserLog("******", "******", "??????????????????", loginExt.getPrimaryId(), loginExt);
    }

    /**
     * ????????????????????????
     *
     * @param userId
     * @return
     */
    public HmWebSite toStartTime(Integer userId) {
        return userMapper.getHmConfigUserId(userId);
    }

    public List<LotteryRq> getUserLottery(Integer userId) {
        return userMapper.getUserLottery(userId);
    }


    public List<HmUserRunning> getUserRunning(userLoginRq userLogin) {
        PageHelper.startPage(userLogin.getPage(), userLogin.getSize());
        return userMapper.getUserRunning(userLogin.getUserId(), userLogin.getStartTime());
    }

    /**
     * ???id??????
     *
     * @param id
     * @param isOpenUp
     */
    public void updateUserIdLottery(Integer id, Boolean isOpenUp) {
        userMapper.updateUserIdLottery(id, isOpenUp);
    }


    /**
     * ????????????
     *
     * @param groupId
     * @param isOpenUp
     */
    public void updateGroupIdLottery(Integer groupId, Boolean isOpenUp, Integer userId) {
        userMapper.updateGroupIdLottery(groupId, isOpenUp, userId);
    }


    /**
     * ??????????????????????????????
     *
     * @param userRq
     * @return
     */
    public boolean verifyBalance(AddUserRq userRq) {
        BalanceInfo balanceInfo = userMapper.selectBalanceInfo(userRq.getParentId());
        if (balanceInfo.getUserLevel() >= 3) {
            if (userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                if (balanceInfo.getUseBalance().compareTo(userRq.getCreditBalance()) < 0 && balanceInfo.getUseRecBalance().compareTo(userRq.getRechargeBalance()) < 0) {
                    return false;
                }
            }
            if (userRq.getReserveModel().equals(UserEnums.ReserveModel.CREDIT.toString())) {
                if (balanceInfo.getUseBalance().compareTo(userRq.getCreditBalance()) < 0) {
                    return false;
                }
            }
            if (userRq.getReserveModel().equals(UserEnums.ReserveModel.RECHARGE.toString())) {
                if (balanceInfo.getUseRecBalance().compareTo(userRq.getRechargeBalance()) < 0) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * ???USER_ID??????????????????
     */
    public void updateStopProfitLoss(StopProfitLossVo stop) {
        if (stop.getStopProfit() == null) {
            stop.setStopProfit(0);
        }
        if (stop.getStopLoss() == null) {
            stop.setStopLoss(0);
        }
        if (stop.getStopProfit() > 10000000 || stop.getStopLoss() > 10000000) {
            ExceptionCast.cast("??????????????????????????????10000000");
        }
        userMapper.updateStopProfitLoss(stop.getUserId(), stop.getStopProfit(), stop.getStopLoss());
    }

    public StopProfitLossVo getStopProfitLoss(Integer userId) {
        return userMapper.getStopProfitLoss(userId);
    }

    /**
     * ????????????????????????
     *
     * @param username
     */
    public void passwordErrorIncrement(String username, String ip) {
        HmUser hmUser = this.findByUsername(username);
        if (null == hmUser) {
            HmUserSon hmUserSon = userSonImpl.findByUsername(username);
            if (null != hmUserSon) {
                HmUserSon upUser = new HmUserSon();
                upUser.setSonId(hmUserSon.getSonId());
                upUser.setPasswordError(hmUserSon.getPasswordError() + 1);
                upUser.setCountError(hmUserSon.getCountError() + 1);
                userSonImpl.updateByPrimaryKeySelective(upUser);
                this.saveLoginError(upUser.getSonId(), username, ip, "????????????", null);
            }
        } else {
            HmUser upUser = new HmUser();
            upUser.setUserId(hmUser.getUserId());
            upUser.setPasswordError(hmUser.getPasswordError() + 1);
            upUser.setCountError(hmUser.getCountError() + 1);
            userMapper.updateByPrimaryKeySelective(upUser);
            this.saveLoginError(upUser.getUserId(), username, ip, "????????????", null);
        }
    }

    /**
     * ???????????????????????????????????????
     */
    public void passwordErrorReset(Boolean enabledSon, Integer userId) {
        if (enabledSon) {
            HmUserSon userSon = new HmUserSon();
            userSon.setSonId(userId);
            userSon.setPasswordError(0);
            userSonImpl.updateByPrimaryKeySelective(userSon);
        } else {
            HmUser byId = this.findById(userId);
            if (byId.getPasswordError() > 0) {
                HmUser hmUser = new HmUser();
                hmUser.setUserId(userId);
                hmUser.setPasswordError(0);
                userMapper.updateByPrimaryKeySelective(hmUser);
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param username
     */
    public void passwordErrorReset(String username, UserLogin loginExt) {
        Example example = new Example(HmUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andLike("parentPath", "%,"+loginExt.getUserId()+",%");
        HmUser hmUser = userMapper.selectOneByExample(example);
        Integer updateId = null;
        if (null == hmUser) {
            HmUserSon hmUserSon = userSonImpl.findByUsername(username);
            //????????????????????????????????????????????????id???????????????id?????????????????????
            if (null != hmUserSon && hmUserSon.getPrimaryId().equals(loginExt.getUserId())) {
                ExceptionCast.castCheck(hmUserSon.getPasswordError() < 5, UcenterCode.UCENTER_UNLOCK);
                HmUserSon userSon = new HmUserSon();
                userSon.setSonId(hmUserSon.getSonId());
                userSon.setPasswordError(0);
                userSon.setUnlockCount(hmUserSon.getUnlockCount() + 1);
                userSonImpl.updateByPrimaryKeySelective(userSon);
                updateId = hmUserSon.getSonId();
            } else {
                log.info("{}????????????[{}]??????", loginExt.getUsername(), username);
                ExceptionCast.cast("????????????????????????");
            }
        } else {
            ExceptionCast.castCheck(hmUser.getPasswordError() < 5, UcenterCode.UCENTER_UNLOCK);
            HmUser upUser = new HmUser();
            upUser.setUserId(hmUser.getUserId());
            upUser.setPasswordError(0);
            upUser.setUnlockCount(hmUser.getUnlockCount() + 1);
            userMapper.updateByPrimaryKeySelective(upUser);
            updateId = upUser.getUserId();
        }
        this.saveLoginError(updateId, username, loginExt.getLoginIp(), "????????????????????????", loginExt.getUsername());
        this.addUserLog(username, "", "????????????????????????", updateId, loginExt);
    }

    public String selectRelation(String username, Boolean son) {
        if (son) {
            return userMapper.selectRelationBySon(username);
        } else {
            return userMapper.selectRelationByUser(username);
        }
    }

    public void saveLoginError(Integer userId, String username, String ip, String logType, String createBy) {
        HmUserLoginError loginError = new HmUserLoginError();
        loginError.setUserId(userId);
        loginError.setUsername(username);
        loginError.setCreateIp(ip);
        loginError.setCreateDay(LocalDateTime.now().toLocalDate());
        loginError.setCreateTime(new Date());
        loginError.setLogType(logType);
        loginError.setCreateBy(createBy);
        userLoginErrorMapper.insertSelective(loginError);
    }

    public List<UserLoginErrorVo> selectLoginError() {
        return userMapper.selectLoginError();
    }

    public List<HmUserLoginError> selectLoginErrorByUser(String username, Integer type) {
        HmUserLoginError loginError = new HmUserLoginError();
        loginError.setUsername(username);
        if(type.equals(201)){
            loginError.setLogType("????????????");
        }else{
            loginError.setLogType("????????????????????????");
        }
        return userLoginErrorMapper.select(loginError);
    }

}
