package com.xzy.user.controller;

import cn.hutool.core.util.StrUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.enums.UserEnums;
import com.xzy.logs.HmLogsService;
import com.xzy.pojo.ctrl.request.LotteryRq;
import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.user.HmMenu;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.*;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.pojo.user.response.UserPrePtRp;
import com.xzy.user.*;
import com.xzy.user.util.ChildrenUtil;
import com.xzy.user.util.VerifyOrderUtil;
import com.xzy.user.vo.UserList;
import com.xzy.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController extends BaseController {

    @DubboReference
    private UserService userService;
    @DubboReference
    private UserPrePtService userPrePtService;
    @DubboReference
    private MenuService menuService;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private VerifyOrderUtil vTransactionUtil;
    @DubboReference
    private UserLoginService userLoginService;
    @DubboReference
    private UserSonService userSonService;
    @DubboReference
    private HmLogsService hmLogsService;

    @GetMapping("/findList")
    public ResponseResult findList(UserRq search) {
        search.setLoginId(getPrimaryId());
        return ResponseData.SUCCESS_LIST(userService.findList(search));
    }

    @GetMapping("/findById")
    public ResponseResult findById(Integer userId) {
        return ResponseData.SUCCESS(userService.findById(userId));
    }

    @GetMapping("/basic/{userId}")
    public ResponseResult findBasicById(@PathVariable Integer userId) {
        return ResponseData.SUCCESS(userService.findBaseById(userId));
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody AddUserRq userRq) {
        if (userService.verifyBalance(userRq)) {
            HmUser hmUser = userService.add(userRq);
            BigDecimal balance = BigDecimal.ZERO;
            BigDecimal Rbalance = BigDecimal.ZERO;
            if (hmUser.getUserLevel() >= 3) {//后桶和公司不需要操作余额
                if (userRq.getCreditBalance().compareTo(BigDecimal.ZERO) == 1) {
                    UpUserBalanceRq userBalanceRq = new UpUserBalanceRq();
                    userBalanceRq.setUserId(hmUser.getUserId());
                    userBalanceRq.setOperation(UserEnums.AccessOperation.DEPOSIT.toString());
                    userBalanceRq.setAmount(userRq.getCreditBalance());
                    if (userRq.getReserveModel().equals(UserEnums.ReserveModel.CREDIT.toString()) ||
                            userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                        userBalanceRq.setReserveModel(UserEnums.ReserveModel.CREDIT);
                        userService.updateCreditBalance(userBalanceRq, getLoginExt());
                    }
                    balance = userRq.getCreditBalance();
                }
                if (userRq.getRechargeBalance().compareTo(BigDecimal.ZERO) == 1) {
                    UpUserBalanceRq userBalanceRq = new UpUserBalanceRq();
                    userBalanceRq.setUserId(hmUser.getUserId());
                    userBalanceRq.setOperation(UserEnums.AccessOperation.DEPOSIT.toString());
                    userBalanceRq.setAmount(userRq.getRechargeBalance());
                    if (userRq.getReserveModel().equals(UserEnums.ReserveModel.RECHARGE.toString()) ||
                            userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                        userBalanceRq.setReserveModel(UserEnums.ReserveModel.RECHARGE);
                        userService.updateRechargeBalance(userBalanceRq, getLoginExt());
                    }
                    Rbalance = userRq.getRechargeBalance();
                }
            }
            /*添加帐号时 添加日志记录*/
            UserLogin userLogin = getLoginExt();
            HmLogs logs = new HmLogs();
            logs.setCreateUser(userLogin.getUsername());//创建人
            if (userLogin.getEnabledSon()) {//是否是子帐号
                logs.setCreateId(userLogin.getPrimaryId().toString());//创建人ID
            } else {
                logs.setCreateId(userLogin.getUserId().toString());//创建人ID
            }
            logs.setOldValue("-");
            logs.setUserId(hmUser.getUserId());
            logs.setCreateIp(getIpAddr());//IP
            logs.setCreateTime(new Date());//创建时间
            for (int i = 1; i <= 4; i++) {
                if (i == 1) {
                    logs.setCreateType("新增帐号名称");
                    logs.setNewValue(hmUser.getUsername());
                    hmLogsService.addHmLogs(logs);
                } else if (i == 2) {
                    logs.setCreateType("新增帐号密码");
                    logs.setNewValue("*******");
                    hmLogsService.addHmLogs(logs);
                } else if (i == 3) {
                    logs.setCreateType("新增帐号额度模式");
                    String val = "";
                    if (userRq.getReserveModel().equals(UserEnums.ReserveModel.CREDIT.toString())) {
                        val = "信用";
                    } else if (userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                        val = "综合";
                    } else {
                        val = "充值";
                    }
                    logs.setNewValue(val);
                    hmLogsService.addHmLogs(logs);
                } else {
                    if (userRq.getReserveModel().equals(UserEnums.ReserveModel.CREDIT.toString()) ||
                            userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                        logs.setCreateType("新增帐号信用额度");
                        logs.setNewValue("" + balance);
                        hmLogsService.addHmLogs(logs);
                    }
                    if (userRq.getReserveModel().equals(UserEnums.ReserveModel.RECHARGE.toString()) ||
                            userRq.getReserveModel().equals(UserEnums.ReserveModel.OMNIBUS.toString())) {
                        logs.setCreateType("新增帐号充值额度");
                        logs.setNewValue("" + Rbalance);
                        hmLogsService.addHmLogs(logs);
                    }
                }

            }
            return ResponseData.SUCCESS(hmUser);
        } else {
            ExceptionCast.cast(UcenterCode.UCENTER_DEPOSIT_NOT);
        }
        return ResponseData.SUCCESS();
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Map<String, Object> map) {
        userService.update(map, getLoginExt());
        return ResponseData.SUCCESS();
    }

    /**
     * 修改无token账号的密码
     * 用于第一次修改密码，需要验证账号状态
     *
     * @param map
     * @return
     */
    @PostMapping("/updatePwd/notoken")
    public ResponseResult updatePwdNotoken(@RequestBody Map<String, String> map) {
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        String username = map.get("username");
        if (StrUtil.isBlank(oldPwd) || StrUtil.isBlank(newPwd) || StrUtil.isBlank(username)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        HmUser hmUser = userService.findByUsername(username);
        if (null != hmUser) {
            if (hmUser.getResetPassword() > 0) {
                //正账号密码修改
                UserLogin loginExt = new UserLogin();
                loginExt.setUsername(hmUser.getUsername());
                loginExt.setUserId(hmUser.getUserId());
                loginExt.setLoginIp(getIpAddr());
                userService.updatePassword(oldPwd, newPwd, loginExt);
            }
        } else {
            HmUserSon userSon = userSonService.findByUsername(username);
            if (null != userSon) {
                if (userSon.getResetPassword() > 0) {
                    //子账号密码修改
                    UserLogin loginExt = new UserLogin();
                    loginExt.setUsername(userSon.getUsername());
                    loginExt.setUserId(userSon.getSonId());
                    loginExt.setLoginIp(getIpAddr());
                    userSonService.updatePassword(oldPwd, newPwd, loginExt);
                }
            } else {
                return ResponseData.FAIL();
            }
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 修改有token账号的密码
     *
     * @param map
     * @return
     */
    @PostMapping("/updatePwd")
    public ResponseResult updateOwnPwd(@RequestBody Map<String, String> map) {
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        if (StrUtil.isBlank(oldPwd) || StrUtil.isBlank(newPwd)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        UserLogin loginExt = getLoginExt();
        if (loginExt.getEnabledSon()) {//是否是子帐号
            userSonService.updatePassword(oldPwd, newPwd, loginExt);
        } else {
            userService.updatePassword(oldPwd, newPwd, loginExt);
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 修改安保密码
     *
     * @param map
     * @return
     */
    @PostMapping("/updateSecondary")
    public ResponseResult updateSecondary(@RequestBody Map<String, String> map) {
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        userService.updateSecondary(oldPwd, newPwd, getLoginExt());
        return ResponseData.SUCCESS();
    }

    @GetMapping("/pre/pt/{userId}")
    public ResponseResult findPrePt(@PathVariable Integer userId) {
        List<UserPrePtRp> userPrePtRps = userPrePtService.selectUserPreByUserId(userId);
        return ResponseData.SUCCESS(userPrePtRps);
    }

    /**
     * 查询会员的占成
     *
     * @param userId
     * @return
     */
    @GetMapping("/pre/membetPt/{userId}")
    public ResponseResult findMemberPrePt(@PathVariable Integer userId) {
        return ResponseData.SUCCESS(userPrePtService.selectUserMemberPreByUserId(userId));
    }

    /**
     * 查询ID数据
     */
    @GetMapping("/add/user/{id}")
    public ResponseData getAddUserById(@PathVariable("id") Integer id) {
        return ResponseData.SUCCESS(userService.findAddUserInfo(id));
    }

    /**
     * 查询登陆人菜单
     */
    @GetMapping("/routers/{son}")
    public ResponseResult getMenuTree(@PathVariable Boolean son) {
        UserLogin userLogin = getLoginExt();
        Map<String, Object> map = new HashMap<>();
        List<HmMenu> hmMenus;
        if (userLogin.getAdmin()) {
            hmMenus = menuService.selMenuList();
        } else {
            if (userLogin.getEnabledSon()) {
                //子账号数据
                hmMenus = menuService.selectBySonId(userLogin.getUserId());
            } else {
                Integer level = userLogin.getUserLevel();
                if (level == 12) {
                    hmMenus = menuService.selectByRoleLevel(10);
                } else {
                    level = level > 5 ? 5 : level;
                    hmMenus = menuService.selectByRoleLevel(level);
                }
            }
        }
        HmUser user = userService.findById(userLogin.getPrimaryId());
        if (!user.getBhStatus()) {
            hmMenus.removeIf(r -> r.getMenuId().equals(51));
        }
        map.put("routers", ChildrenUtil.childrenMenuList(hmMenus, 0));
        return ResponseData.SUCCESS(map);
    }

    /**
     * 登出系统
     *
     * @return
     */
    @GetMapping("/userlogout")
    public ResponseResult logout() {
        //取出cookie中的用户身份令牌
        try {
            // String uid=request.getHeader("uid");
            UserLogin userLogin = getLoginExt();
            userLogin.setLoginIp(getIpAddr());
            userLoginService.saveLogoutLog(userLogin);//添加登出日志
            if (!loginUtil.delToken(userLogin.getUid())) {
                return ResponseData.ERROR(CommonCode.FAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 修改占成
     *
     * @return
     */
    @PutMapping("/edit/pt")
    public ResponseResult editUserPt(@RequestBody AddUserRq prePt) {
        vTransactionUtil.verifyToBet(prePt.getUserId());//验证下线中是否有注单
        userPrePtService.updateLowerPt(prePt.getPrePt(), prePt.getUserId());
        userPrePtService.updateUserPrePt(prePt.getUserId());
        return ResponseData.SUCCESS();
    }

    /**
     * 修改预设占成
     *
     * @return
     */
    @PutMapping("/edit/pre")
    public ResponseResult editUserPre(@RequestBody AddUserRq prePt) {
        userPrePtService.updateLowerPrePt(prePt.getPrePt(), prePt.getUserId());
        userPrePtService.updateUserPresPt(prePt.getUserId());
        return ResponseData.SUCCESS();
    }

    @GetMapping("/actual/pt/{userId}")
    public ResponseResult getActualPt(@PathVariable Integer userId) {
        return ResponseData.SUCCESS(userPrePtService.selectUserActualPt(getLoginExt().getUserLevel(), userId));
    }

    @DeleteMapping("/delUserId/{userId}")
    public ResponseResult delUserId(@PathVariable Integer userId) {
        ExceptionCast.cast("功能暂停中！！！");
        UserLogin loginExt = getLoginExt();
        userService.delUserId(userId, loginExt, getIpAddr());
        return ResponseData.SUCCESS();
    }

    /**
     * 修改账号额度
     *
     * @param userBalanceRq
     * @return
     */
    @PutMapping("/balance")
    public ResponseResult updateUserBalance(@RequestBody UpUserBalanceRq userBalanceRq) {
        if (userBalanceRq.getReserveModel().toString().equals(UserEnums.ReserveModel.RECHARGE.toString())) {
            userService.updateRechargeBalance(userBalanceRq, getLoginExt());
        } else {
            userService.updateCreditBalance(userBalanceRq, getLoginExt());
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 修改账号额度
     *
     * @param userBalanceRq
     * @return
     */
    @PutMapping("/allBalance")
    public ResponseResult allBalance(@RequestBody UpUserBalanceRq userBalanceRq) {
        userService.allBalance(userBalanceRq, getLoginExt());
        return ResponseData.SUCCESS();
    }

    @PutMapping("/nullMemBalance")
    public ResponseResult nullMemBalance(@RequestBody UpUserBalanceRq userBalanceRq) {
        userService.nullMemBalance(userBalanceRq.getUserId(), userBalanceRq.getReserveModel().toString(), getLoginExt().getUsername());
        return ResponseData.SUCCESS();
    }

    /**
     * 查询占成
     *
     * @return
     */
    @GetMapping("/countOrder/{userId}")
    public ResponseResult countOrder(@PathVariable Integer userId) {
        Map<String, Object> map = new HashMap<>();
        boolean preset = userService.countOrder(userId);
        if (preset) {//有注单
            List<UserPrePtRp> list = userPrePtService.selectUserPreByUserId(userId);
            map.put("list", list);
        }
        map.put("preset", preset);
        return ResponseData.SUCCESS(map);
    }

    @GetMapping("/lower")
    public ResponseResult lowerMember(Integer page, Integer size) {
        UserRq search = new UserRq();
        search.setPage(page);
        search.setSize(size);
        search.setLoginId(getPrimaryId());
        search.setParentId(search.getLoginId());
        search.setReserveModel("ALL");
        search.setUserType("allMember");
        search.setStatus("OPEN");
        List<UserInfo> list = userService.findList(search);
        List<UserList> userList = new ArrayList<>();
        list.forEach(info -> {
            UserList user = new UserList();
            user.setUsername(info.getUsername());
            user.setNickName(info.getNickName());
            user.setParentName(info.getParentName());
            user.setMarketOpen(info.getMarketOpen());
            if (info.getReserveModel().equals(UserEnums.ReserveModel.CREDIT.toString())) {
                user.setBalance(info.getUseCreditBalance());
            } else {
                user.setBalance(info.getUseRechargeBalance());
            }
            userList.add(user);
        });
        return ResponseData.SUCCESS_LIST(userList);
    }

    /**
     * 查询公司彩种
     *
     * @return
     */
    @GetMapping("/userLottery/{userId}")
    public ResponseResult getUserLottery(@PathVariable Integer userId) {
        return ResponseData.SUCCESS(userService.getUserLottery(userId));
    }

    /**
     * 查询余额变动
     *
     * @return
     */
    @GetMapping("/userRunning")
    public ResponseResult getUserRunning(userLoginRq userLogin) {
        return ResponseData.SUCCESS_LIST(userService.getUserRunning(userLogin));
    }

    /**
     * 按ID修改彩种
     *
     * @return
     */
    @GetMapping("/updateUserIdLottery")
    public ResponseResult updateUserIdLottery(LotteryRq user) {
        userService.updateUserIdLottery(user.getId(), user.getIsOpenUp());
        return ResponseData.SUCCESS();
    }

    /**
     * 按传进去的组ID修改彩种
     *
     * @return
     */
    @GetMapping("/updateGroupIdLottery")
    public ResponseResult updateGroupIdLottery(LotteryRq user) {
        userService.updateGroupIdLottery(user.getGroupId(), user.getIsOpenUp(), user.getUserId());
        return ResponseData.SUCCESS();
    }

    /**
     * 按USER_ID修改止盈止亏
     *
     * @return
     */
    @GetMapping("/updateStopProfitLoss")
    public ResponseResult updateStopProfitLoss(StopProfitLossVo stop) {
        stop.setUserId(getPrimaryId());
        userService.updateStopProfitLoss(stop);
        return ResponseData.SUCCESS();
    }

    /**
     * 查询止盈止亏
     *
     * @return
     */
    @GetMapping("/getStopProfitLoss")
    public ResponseResult getStopProfitLoss() {
        return ResponseData.SUCCESS(userService.getStopProfitLoss(getPrimaryId()));
    }

    @PostMapping("/reset/password/{username}")
    public ResponseResult resetPwd(@PathVariable String username) {
        UserLogin loginExt = getLoginExt();
        log.info("{}解锁账号[{}]", loginExt.getUsername(), username);
        userService.passwordErrorReset(username, loginExt);
        return ResponseData.SUCCESS();
    }

    @GetMapping("/relation")
    public ResponseResult getRelation(String username, Boolean son) {
        return ResponseData.SUCCESS(userService.selectRelation(username, son));
    }

    /**
     * 查询密码登录错误账号
     * @return
     */
    @GetMapping("/login/error")
    public ResponseResult getLoginError() {
        return ResponseData.SUCCESS(userService.selectLoginError());
    }

    /**
     * 查询密码登录错误账号详细数据
     * @return
     */
    @GetMapping("/login/error/{username}/{type}")
    public ResponseResult getLoginErrorByUsername(@PathVariable String username,@PathVariable Integer type) {
        return ResponseData.SUCCESS(userService.selectLoginErrorByUser(username,type));
    }

}
