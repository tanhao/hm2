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
     * 条件查询
     *
     * @param search 查询条件
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
     * 根据Id查询
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
     * 新增
     *
     * @param userRq
     */
    public HmUser add(AddUserRq userRq) {
        String username = UserCk.checkUserName(userRq.getUsername());
        String password = UserCk.checkPassword(userRq.getPassword());
        HmUser user = this.findByUsername(username);
        ExceptionCast.castCheck(null != user, UcenterCode.UCENTER_USERNAME_EXIST);
        //检查子账号是否存在
        //TODO
        HmUserSon son = userSonImpl.findByUsername(username);
        ExceptionCast.castCheck(null != son, UcenterCode.UCENTER_USERNAME_EXIST);
        HmUser hmUser = new HmUser();
        hmUser.setUsername(username);
        //检查姓名并赋值
        hmUser.setNickName(UserCk.checkFullName(userRq.getNickName()));
        hmUser.setLoginIpBound(userRq.getLoginIpBound());
        hmUser.setPassword(password);
        hmUser.setCreated(new Date());
        hmUser.setUserType(userRq.getUserType());
        //获取上级数据，赋值
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
            String secondary = UserCk.checkPassword(userRq.getSecondary());//保存2级密码
            hmUser.setSecondary(secondary);
        } else if (superUser.getUserLevel() == 2) {
            if (hmUser.getUserType().equals("AGENT")) {
                hmUser.setDisk(userRq.getDisk());
            }
        }
        if (hmUser.getUserType().equals("AGENT")) {//添加补货状态
            hmUser.setBhStatus(userRq.getBhStatus());
        } else {
            hmUser.setBhStatus(false);
        }
        hmUser.setJumpType(UserEnums.JumpType.XZ);
        //log.info("add hm_user:{}", hmUser);
        //1.添加用户信息
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
        if (hmUser.getUserLevel() <= 2) {//后桶和公司
            userMapper.insertCompnayInfo(userDTO);
        } else if (hmUser.getUserLevel() == 3) {//一级代理
            userMapper.insertAgent1Info(userDTO);
        } else {//二到10级代理会员
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
        //设置账号基本数据
        HmUserBase hmUserBase = new HmUserBase();
        //复制base属性
        BeanUtils.copyProperties(userRq, hmUserBase);
        //保存用户信息后回显id
        hmUserBase.setUserId(hmUser.getUserId());
        hmUserBase.setRentalStartDate(userRq.getRentalStartDate());
        //上级数据
        HmUserBase upBase = userBaseMapper.selectByPrimaryKey(hmUser.getParentId());

        if (hmUser.getUserType().equals("MEMBER")) {
            //验证公司已开账号数量
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
        //验证盘口,每级验证
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
        //2.添加base数据
        userBaseMapper.insertSelective(hmUserBase);


        log.info("添加user成功");
        //不同等级有不同处理数据
        if (hmUser.getUserLevel() == 2) {
            userMapper.updateCompanyId(hmUser.getUserId(), hmUser.getUserId());//添加公司时进去修改自己的公司ID
            //添加搜索码数据
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
            userMapper.insertHmWebSiteInfo(hmConfig);//添加config表

        }
        //保存预设占层
        List<HmUserPrePt> savePrePts = userRq.getPrePt();
        savePrePts.stream().forEach(pre -> pre.setUserId(hmUser.getUserId()));
        userPrePtService.savePrePt(savePrePts, superUser.getUserId());
        return hmUser;
    }


    /**
     * 修改用户余额
     *
     * @param userBalanceRq 余额
     * @param userLogin     登录类
     */
    @Synchronized
    public void updateCreditBalance(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        log.info("修改信息 updateCreditBalance：{}", userBalanceRq.toString());
        ExceptionCast.castCheck(null == userBalanceRq.getUserId(), CommonCode.INVALID_PARAM);
        this.getCheckEnabledSon(userBalanceRq, userLogin);
        //验证余额必须大于0
        Boolean checkAmount = userBalanceRq.getAmount().compareTo(BigDecimal.ZERO) <= 0;
        ExceptionCast.castCheck(checkAmount, CommonCode.INVALID_PARAM);
        //查询用户余额信息
        BalanceInfo balanceInfo = userMapper.selectBalanceInfo(userBalanceRq.getUserId());
        //初始化修改用户信息
        HmUserBase hmUserBase = userBaseMapper.selectByPrimaryKey(userBalanceRq.getUserId());
        //初始化上级用户信息
        HmUserBase superUserBase = userBaseMapper.selectByPrimaryKey(this.findById(userBalanceRq.getUserId()).getParentId());
        String type = userBalanceRq.getReserveModel().toString();
        if (UserEnums.AccessOperation.DEPOSIT.toString().equals(userBalanceRq.getOperation())) {
            if (balanceInfo.getUserLevel() > 3) {
                BigDecimal maxUse = balanceInfo.getSuperUseBalance();
                if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_DEPOSIT_NOT);
                }
               /* if(userBalanceRq.getAmount().compareTo(balanceInfo.getBalance().subtract(balanceInfo.getUseBalance())) < 1 ){
                    ExceptionCast.cast("大于可修改的范围请重新输入!");
                }*/
                //扣除上级使用余额
                BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            }
            //存款用户加上余额
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
        } else if (UserEnums.AccessOperation.RECYCLE.toString().equals(userBalanceRq.getOperation())) {
            //回收验证
            //取剩余可用的余额和上线给的最大余额做对比取最小
            BigDecimal maxUse = null;//balanceInfo.getUseBalance().min(balanceInfo.getBalance());
            //会员直接验正余额
            if (balanceInfo.getUserType().equals("AGENT")) {
                maxUse = balanceInfo.getUseBalance().min(balanceInfo.getBalance());
            } else {
                maxUse = balanceInfo.getBalance();
            }
            if ((balanceInfo.getBalance().subtract(userBalanceRq.getAmount())).compareTo(balanceInfo.getBalance().subtract(balanceInfo.getUseBalance())) == -1) {
                ExceptionCast.cast("小于可修改的范围请重新输入!");
            }
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast(UcenterCode.UCENTER_WITHDRAWAL_NOT);
            }
            //存款用户减去余额
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            if (balanceInfo.getUserLevel() >= 3) {
                //加回上级使用余额
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
            }
        }
    }

    public void getCheckEnabledSon(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        if (userLogin.getEnabledSon()) {//是否是子帐号
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
        log.info("修改信息：{}", userBalanceRq.toString());
        this.getCheckEnabledSon(userBalanceRq, userLogin);
        ExceptionCast.castCheck(StrUtil.isBlank(userBalanceRq.getOperation()), CommonCode.INVALID_PARAM);
        ExceptionCast.castCheck(null == userBalanceRq.getUserId(), CommonCode.INVALID_PARAM);
        //验证余额必须大于0
        Boolean checkAmount = userBalanceRq.getAmount().compareTo(BigDecimal.ZERO) <= 0;
        ExceptionCast.castCheck(checkAmount, CommonCode.INVALID_PARAM);
        //查询用户余额信息
        BalanceInfo balanceInfo = userMapper.selectBalanceInfo(userBalanceRq.getUserId());
        //初始化修改用户信息
        HmUserBase hmUserBase = userBaseMapper.selectByPrimaryKey(userBalanceRq.getUserId());
        //初始化上级用户信息
        HmUserBase superUserBase = userBaseMapper.selectByPrimaryKey(this.findById(userBalanceRq.getUserId()).getParentId());
        String type = userBalanceRq.getReserveModel().toString();
        if (UserEnums.AccessOperation.DEPOSIT.toString().equals(userBalanceRq.getOperation())) {
            if (balanceInfo.getUserLevel() > 3) {//公司以上余额无限
                //当前修改的是一级代理以上的需要验证存款上级余额是否足够
                BigDecimal maxUse = balanceInfo.getSuperUseRecBalance();
                if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_DEPOSIT_NOT);
                }
                //扣除上级使用余额
                BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            }
            //存款用户加上余额
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
        } else if (UserEnums.AccessOperation.RECYCLE.toString().equals(userBalanceRq.getOperation())) {
            //回收验证
            //取剩余可用的余额和上线给的最大余额做对比取最小
            BigDecimal maxUse = balanceInfo.getUseRecBalance().min(balanceInfo.getRecBalance());
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast(UcenterCode.UCENTER_WITHDRAWAL_NOT);
            }
            //存款用户减去余额
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            if (balanceInfo.getUserLevel() >= 3) {
                //加回上级使用余额
                this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLogin.getUsername(), "");
            }
        } else if (UserEnums.AccessOperation.WITHDRAWAL.toString().equals(userBalanceRq.getOperation())) {
            //取款验证，用户当前余额是否足够 可提现额度= MAX(余额-总充值额度,0)
            BigDecimal maxUse = balanceInfo.getUseRecBalance().subtract(balanceInfo.getRecBalance()).max(BigDecimal.ZERO);
            //BigDecimal maxUse = new BigDecimal(Math.max(balanceInfo.getUseRecBalance().subtract(balanceInfo.getRecBalance()).intValue(),0));//balanceInfo.getUseRecBalance();
            if (userBalanceRq.getAmount().compareTo(maxUse) == 1) {
                ExceptionCast.cast("余额不足");
            }
            //存款用户减去余额
            BigDecimal amount = userBalanceRq.getAmount().multiply(new BigDecimal(-1));
            this.insertRunning(hmUserBase.getUserId(), "def", userBalanceRq.getOperation(), type, amount, userLogin.getUsername(), "");
            //加回上级使用余额
            //TODO 蒸发会员余额？
            // this.insertRunning(superUserBase.getUserId(), "use", userBalanceRq.getOperation(), type, userBalanceRq.getAmount(), userLoginExt.getUsername(), "");
        }
    }

    /**
     * 清空会员额度
     *
     * @param userId
     */
    @Synchronized
    public void nullMemBalance(Integer userId, String type, String name) {
        log.info("清零会员额度：{}", userId);
        ExceptionCast.castCheck(null == userId, CommonCode.INVALID_PARAM);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        resultMap.put("reserveModel", type);
        resultMap.put("updateBy", name);
        userMapper.nullMemBalance(resultMap);
    }

    /**
     * 全回收额度
     *
     * @param userBalanceRq
     * @param userLogin
     */
    @Synchronized
    public void allBalance(UpUserBalanceRq userBalanceRq, UserLogin userLogin) {
        log.info("全回收额度：{}", userBalanceRq.toString());
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
     * 修改
     */
    public void update(Map<String, Object> map, UserLogin userLogin) {
        log.info("修改信息参数：{}", map);
        //判断是否有修改用户的ID
        ExceptionCast.castCheck(ObjectUtil.isNull(map.get("userId")), CommonCode.INVALID_PARAM);
        Integer userId = (Integer) map.get("userId");
        HmUser oldUser = userMapper.selectByPrimaryKey(userId);
        if (null == checkIdByPath(userLogin.getPrimaryId(), userId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if (null == oldUser) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //标记是否有需要修改的值
        Boolean flagUser = false, flagBase = false;
        //需要修改的对象
        HmUser hmUser = new HmUser();
        hmUser.setUserId(userId);

        HmUserBase hmUserBase = new HmUserBase();
        hmUserBase.setUserId(userId);
        if (oldUser.getUserLevel() <= 2) {//后桶和公司可以修改安保密码
            if (ObjectUtil.isNotNull(map.get("secondary"))) {//修改安保密码
                flagUser = true;
                String secondary = UserCk.checkPassword(map.get("secondary").toString());
                hmUser.setSecondary(secondary);
            }
        }

        //修改密码
        if (ObjectUtil.isNotNull(map.get("password"))) {
            flagUser = true;
            //验证密码
            String password = UserCk.checkPassword(map.get("password").toString());
            hmUser.setResetPassword(1);
            hmUser.setPassword(password);
            //修改密码后提出账号
            loginUtil.delTokenById(new String[]{hmUser.getUserId().toString()});
        }
        //修改名称
        if (ObjectUtil.isNotNull(map.get("nickName"))) {
            flagUser = true;
            hmUser.setNickName(UserCk.checkFullName(map.get("nickName").toString()));
        }
        //修改绑定登录IP
        if (ObjectUtil.isNotNull(map.get("loginIpBound"))) {
            flagUser = true;
            hmUser.setLoginIpBound(map.get("loginIpBound").toString());
        }
        //修改租金
        if (ObjectUtil.isNotNull(map.get("rental"))) {
            flagBase = true;
            hmUserBase.setRental(new BigDecimal(map.get("rental").toString()));
        }
        //修改最高盈利
        if (ObjectUtil.isNotNull(map.get("maxWin"))) {
            flagBase = true;
            hmUserBase.setMaxWin(Integer.valueOf(map.get("maxWin").toString()));
        }
        if (ObjectUtil.isNotNull(map.get("marketSelect"))) {//修改选择投注模式
            flagBase = true;
            hmUserBase.setMarketSelect((String) map.get("marketSelect"));
        }
        if (ObjectUtil.isNotNull(map.get("maxUser"))) {//修改可以开会员数
            flagBase = true;
            hmUserBase.setMaxUser((Integer) map.get("maxUser"));
        }
        //修改开放盘口 暂时没有
        if (ObjectUtil.isNotNull(map.get("marketOpen"))) {//修改开放盘口
            flagBase = true;
            hmUserBase.setMarketOpen((String) map.get("marketOpen"));
        }
        //修改账号状态
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
            } else {//修改停用和冻结时要修改他的全部下线
                userMapper.updateStatus(userId, status);
                List<HmUser> user = userMapper.selectParentPath(userId);
                HmUserBase ub = userBaseMapper.selectByPrimaryKey(userId);
                HmUser hu = this.findById(userId);
                if (status.equals("CLOSE")) {
                    if (hu.getUserType().equals("MEMBER")) {//会员帐号清零
                        this.nullMemBalance(hu.getUserId(), ub.getReserveModel(), userLogin.getNickName());
                    } else {//代理帐号 全回收
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
        if (oldUser.getUserLevel() == 2) { //修改搜索码数据
            if (ObjectUtil.isNotNull(map.get("disk"))) {
                if (oldUser.getUserType().equals("AGENT")) {
                    hmUser.setDisk((boolean) map.get("disk"));
                }
            }
            if (ObjectUtil.isNotNull(map.get("securityCode"))) {
                String code = UserCk.checkSecurityCode(map.get("securityCode").toString());
                int num = userMapper.getSecurityCode(code);//验证是否重复
                if (num >= 1) {
                    ExceptionCast.cast(UcenterCode.UCENTER_SECURITY_CODE_EXIST);
                }
                userMapper.updateSecurityCode(hmUser.getUserId(), code);
            }
            if (ObjectUtil.isNotNull(map.get("siteName"))) {
                HmWebSite webSite = userMapper.getHmConfigUserId(userId);//查询是否有当前数据 没有就添加
                String siteName = map.get("siteName").toString();
                if (webSite != null) {
                    userMapper.updateSiteName(hmUser.getUserId(), siteName);
                } else {
                    HmWebSite web = new HmWebSite();
                    web.setUserId(userId);
                    web.setSiteName(siteName);
                    userMapper.insertHmWebSiteInfo(web);//添加config表
                }
            }
        }
        //修改赚赔
        if (ObjectUtil.isNotNull(map.get("gainOdds"))) {
            String gainOdds = map.get("gainOdds").toString();
            UserInfo userInfo = userMapper.selSwitchStatus(userId);
            if (gainOdds.equals(userInfo.getGainOdds())) {
                ExceptionCast.cast(CommonCode.SUPERIOR_UPDATE);
            }
            //调用存储过程修改数据
            userMapper.updateBaseGainOdds(userId, gainOdds);
        }
        //修改控盘
        if (ObjectUtil.isNotNull(map.get("controlOdds"))) {
            String controlOdds = map.get("controlOdds").toString();
            UserInfo userInfo = userMapper.selSwitchStatus(userId);
            if (controlOdds.equals(userInfo.getControlOdds())) {
                ExceptionCast.cast(CommonCode.SUPERIOR_UPDATE);
            }
            //调用存储过程修改数据
            userMapper.updateBaseControlOdds(userId, controlOdds);
        }
        //修改补货状态
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
            //进入修改用户表
            userMapper.updateByPrimaryKeySelective(hmUser);
        }
        if (flagBase) {
            //进入修改附加数据表
            userBaseMapper.updateByPrimaryKeySelective(hmUserBase);
        }
    }


    //查询id添加下级的原数据
    public UserInfo findAddUserInfo(Integer userId) {
        //验证参数合法性
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
     * 统计下线总数
     *
     * @param userId
     * @return
     */
    public Integer selCountDownlineTotal(Integer userId) {
        return userMapper.selCountDownlineTotal(userId);
    }

    public boolean checkSecondary(Integer userId, String secondary) {
        HmUser hmUser = userMapper.selectByPrimaryKey(userId);//查询
        return BCryptUtil.matches(secondary, hmUser.getSecondary());
    }

    /**
     * 是否有注单
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
     * 是否有注单
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
     * 查询下线代理
     *
     * @param userId
     * @param disk
     * @return
     */
    public List<HmUser> selectLower(Integer userId, Boolean disk) {
        return userMapper.selectLower(userId, disk);
    }

    /**
     * 删除没有下级的账号
     *
     * @param userId
     * @param loginExt
     */
    public void delUserId(Integer userId, UserLogin loginExt, String ip) {
        HmUser upHmUser = this.checkIdByPath(loginExt.getUserId(), userId);
        if (null != upHmUser) {
            log.info("{}删除账号[{}]", loginExt.getUsername(), upHmUser.getUsername());
            userMapper.delUserId(userId);
            loginUtil.delTokenById(new String[]{userId.toString()});
            HmLogs logs = new HmLogs();
            logs.setCreateUser(loginExt.getUsername());//创建人
            logs.setCreateId(loginExt.getUserId().toString());//创建人ID
            logs.setUserId(userId);
            logs.setCreateIp(ip);//IP
            logs.setCreateTime(new Date());//创建时间
            logs.setCreateType("删除账号");
            logs.setNewValue(upHmUser.getUsername());
            logs.setOldValue(upHmUser.getUsername());
            hmLogsService.addHmLogs(logs);
        }
    }

    /***
     * 余额变动
     * @param userId
     * @param category 类型
     * @param operation 处理方式
     * @param type     充值账号/信用账号
     * @param amount    金额
     * @param createBy  修改人
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
     * 验证id是否是登陆id的下级
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

    //查询会员余额信息
    public MemBalanceInfo selectTodayInfo(Integer loginId) {
        MemBalanceInfo balanceInfo = userMapper.selectTodayInfo(loginId, DateUtil.getAccountDay().toString());
        return balanceInfo;
    }

    /**
     * 修改登陆密码
     *
     * @param oldPwd
     * @param newPwd
     */
    public void updatePassword(String oldPwd, String newPwd, UserLogin loginExt) {
        //验证旧密码==新密码
        ExceptionCast.castCheck(StrUtil.equals(oldPwd, newPwd), UcenterCode.UCENTER_NO_PWD);
        //查出账号数据
        HmUser hmUser = userMapper.selectByPrimaryKey(loginExt.getUserId());
        //验证旧密码
        boolean matches = BCryptUtil.matches(oldPwd, hmUser.getPassword());
        ExceptionCast.castCheck(!matches, UcenterCode.UCENTER_OLDPASSWORD_ERROR);
        String password = UserCk.checkPassword(newPwd);
        HmUser user = new HmUser();
        user.setUserId(loginExt.getUserId());
        user.setPassword(password);
        user.setUpdated(new Date());
        user.setResetPassword(0);
        log.info(hmUser.getUsername() + "修改自己密码:" + oldPwd + "=>" + newPwd);
        userMapper.updateByPrimaryKeySelective(user);
        this.addUserLog("******", "******", "修改账号密码", loginExt.getUserId(), loginExt);
    }

    /**
     * 添加账号日志记录
     */
    public void addUserLog(String oldValue, String newValue, String type, Integer updateId, UserLogin userLogin) {
        try {
            HmLogs logs = new HmLogs();
            logs.setCreateUser(userLogin.getUsername());//创建人
            logs.setCreateId(userLogin.getUserId().toString());//创建人ID
            logs.setOldValue(oldValue);
            logs.setUserId(updateId);
            logs.setCreateIp(userLogin.getLoginIp());//IP
            logs.setCreateTime(new Date());//创建时间
            logs.setCreateType(type);
            logs.setNewValue(newValue);
            hmLogsService.addHmLogs(logs);
        } catch (Exception e) {
            log.info("添加账号日志记录报错", e);
        }
    }

    /**
     * 修改安保密码
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
        this.addUserLog("******", "******", "修改安保密码", loginExt.getPrimaryId(), loginExt);
    }

    /**
     * 查询公司营业时间
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
     * 按id修改
     *
     * @param id
     * @param isOpenUp
     */
    public void updateUserIdLottery(Integer id, Boolean isOpenUp) {
        userMapper.updateUserIdLottery(id, isOpenUp);
    }


    /**
     * 按组修改
     *
     * @param groupId
     * @param isOpenUp
     */
    public void updateGroupIdLottery(Integer groupId, Boolean isOpenUp, Integer userId) {
        userMapper.updateGroupIdLottery(groupId, isOpenUp, userId);
    }


    /**
     * 验证上级余额是否足够
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
     * 按USER_ID修改止盈止亏
     */
    public void updateStopProfitLoss(StopProfitLossVo stop) {
        if (stop.getStopProfit() == null) {
            stop.setStopProfit(0);
        }
        if (stop.getStopLoss() == null) {
            stop.setStopLoss(0);
        }
        if (stop.getStopProfit() > 10000000 || stop.getStopLoss() > 10000000) {
            ExceptionCast.cast("止盈止亏金额不能超过10000000");
        }
        userMapper.updateStopProfitLoss(stop.getUserId(), stop.getStopProfit(), stop.getStopLoss());
    }

    public StopProfitLossVo getStopProfitLoss(Integer userId) {
        return userMapper.getStopProfitLoss(userId);
    }

    /**
     * 密码错误次数增加
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
                this.saveLoginError(upUser.getSonId(), username, ip, "密码错误", null);
            }
        } else {
            HmUser upUser = new HmUser();
            upUser.setUserId(hmUser.getUserId());
            upUser.setPasswordError(hmUser.getPasswordError() + 1);
            upUser.setCountError(hmUser.getCountError() + 1);
            userMapper.updateByPrimaryKeySelective(upUser);
            this.saveLoginError(upUser.getUserId(), username, ip, "密码错误", null);
        }
    }

    /**
     * 登录成功后密码错误次数清空
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
     * 解锁错误密码账号
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
            //查出子账号数据，不为空并且登陆人id为子账号主id，才能进行修改
            if (null != hmUserSon && hmUserSon.getPrimaryId().equals(loginExt.getUserId())) {
                ExceptionCast.castCheck(hmUserSon.getPasswordError() < 5, UcenterCode.UCENTER_UNLOCK);
                HmUserSon userSon = new HmUserSon();
                userSon.setSonId(hmUserSon.getSonId());
                userSon.setPasswordError(0);
                userSon.setUnlockCount(hmUserSon.getUnlockCount() + 1);
                userSonImpl.updateByPrimaryKeySelective(userSon);
                updateId = hmUserSon.getSonId();
            } else {
                log.info("{}解锁账号[{}]失败", loginExt.getUsername(), username);
                ExceptionCast.cast("请用正账号解锁！");
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
        this.saveLoginError(updateId, username, loginExt.getLoginIp(), "解锁登录错误次数", loginExt.getUsername());
        this.addUserLog(username, "", "解锁登录错误次数", updateId, loginExt);
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
            loginError.setLogType("密码错误");
        }else{
            loginError.setLogType("解锁登录错误次数");
        }
        return userLoginErrorMapper.select(loginError);
    }

}
