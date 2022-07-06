package com.xzy.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.logs.HmLogsService;
import com.xzy.pojo.logs.HmUserSonLog;
import com.xzy.pojo.user.HmMenuSon;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.HmUserSon;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.request.UserRq;
import com.xzy.user.UserSonService;
import com.xzy.user.dao.MenuSonMapper;
import com.xzy.user.dao.UserSonMapper;
import com.xzy.user.util.UserCk;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@DubboService(interfaceClass = UserSonService.class)
public class UserSonImpl implements UserSonService {
    @Autowired
    UserSonMapper userSonMapper;
    @Autowired
    MenuSonMapper menuSonMapper;
    @Autowired
    UserServiceImpl userService;
    @DubboReference
    private HmLogsService hmLogsService;

    @Override
    public List<HmUserSon> findAll() {
        return userSonMapper.selectAll();
    }

    @Override
    public List<HmUserSon> findList(UserRq searchMap, Integer primaryId) {
        PageHelper.startPage(searchMap.getPage(), searchMap.getSize());
        Example example = new Example(HmUserSon.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 用户名
            if (ObjectUtil.isNotNull(searchMap.getUsername())) {
                criteria.andLike("username", "%" + searchMap.getUsername() + "%");
            }
            if (ObjectUtil.isNotNull(searchMap.getStatus())) {
                criteria.andEqualTo("status", searchMap.getStatus());
            }
        }
        criteria.andEqualTo("primaryId", primaryId);
        List<HmUserSon> hmUserSons = userSonMapper.selectByExample(example);
        hmUserSons.forEach(son -> {
            HmMenuSon hmMenuSon = new HmMenuSon();
            hmMenuSon.setSonId(son.getSonId());
            son.setMenus(menuSonMapper.select(hmMenuSon).stream().map(HmMenuSon::getMenuId).collect(Collectors.toList()));
        });
        return hmUserSons;
    }

    @Override
    public HmUserSon findById(Integer sonId, Integer primaryId) {
        HmUserSon son = new HmUserSon();
        son.setSonId(sonId);
        son.setPrimaryId(primaryId);
        HmUserSon userSon = userSonMapper.selectOne(son);
        return userSon;
    }

    @Override
    public HmUserSon findByUsername(String username) {
        HmUserSon hmUserSon = new HmUserSon();
        hmUserSon.setUsername(username);
        return userSonMapper.selectOne(hmUserSon);
    }

    @Override
    public void add(HmUserSon hmUserSon, UserLogin loginExt) {
        UserCk.checkUserName(hmUserSon.getUsername());
        UserCk.checkFullName(hmUserSon.getNickName());
        String password = UserCk.checkPassword(hmUserSon.getPassword());
        //检查子账号是否存在
        HmUser user = userService.findByUsername(hmUserSon.getUsername());
        ExceptionCast.castCheck(null != user, UcenterCode.UCENTER_USERNAME_EXIST);
        HmUserSon son = this.findByUsername(hmUserSon.getUsername());
        ExceptionCast.castCheck(null != son, UcenterCode.UCENTER_USERNAME_EXIST);

        hmUserSon.setPassword(password);
        hmUserSon.setCreated(new Date());
        userSonMapper.insertSelective(hmUserSon);
        hmUserSon.getMenus().forEach(m -> {
            HmMenuSon hmMenuSon = new HmMenuSon(hmUserSon.getSonId(), m);
            menuSonMapper.insertSelective(hmMenuSon);
        });

        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                this.addUserSonLog("-", hmUserSon.getUsername(), "新增子帐号 帐号", hmUserSon.getSonId(), loginExt);
            } else if (i == 2) {
                this.addUserSonLog("-", hmUserSon.getNickName(), "新增子帐号 名称", hmUserSon.getSonId(), loginExt);
            } else if (i == 3) {
                this.addUserSonLog("-", "******", "新增子帐号 密码", hmUserSon.getSonId(), loginExt);
            } else {
                this.addUserSonLog("-", hmUserSon.getMenus().toString(), "新增子帐号 权限", hmUserSon.getSonId(), loginExt);
            }
        }
    }

    @Override
    public void update(HmUserSon hmUserSon, UserLogin loginExt) {
        HmUserSon son = new HmUserSon();
        son.setSonId(hmUserSon.getSonId());
        son.setPrimaryId(loginExt.getPrimaryId());
        HmUserSon userSon = userSonMapper.selectOne(son);
        if (userSon == null) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        HmUserSon upSon = new HmUserSon();
        if (ObjectUtil.isNotNull(hmUserSon.getPassword())) {
            String password = UserCk.checkPassword(hmUserSon.getPassword());
            upSon.setPassword(password);
            upSon.setResetPassword(1);
            this.addUserSonLog("******", "******", "修改子帐号 密码", hmUserSon.getSonId(), loginExt);
        }
        upSon.setSonId(hmUserSon.getSonId());
        upSon.setNickName(hmUserSon.getNickName());
        if (StrUtil.isNotBlank(hmUserSon.getNickName()) && !userSon.getNickName().equals(hmUserSon.getNickName())) {
            this.addUserSonLog(userSon.getNickName(), hmUserSon.getNickName(), "修改子帐号 名称", hmUserSon.getSonId(), loginExt);
        }
        HmMenuSon hmMenuSon = new HmMenuSon();
        hmMenuSon.setSonId(userSon.getSonId());
        userSon.setMenus(menuSonMapper.select(hmMenuSon).stream().map(HmMenuSon::getMenuId).collect(Collectors.toList()));
        if (hmUserSon.getMenus() != null && !userSon.getMenus().toString().equals(hmUserSon.getMenus().toString())) {
            this.addUserSonLog(userSon.getMenus().toString(), hmUserSon.getMenus().toString(), "修改子帐号 权限", hmUserSon.getSonId(), loginExt);
            HmMenuSon dele = new HmMenuSon();
            //先删除菜单，在重新添加
            dele.setSonId(upSon.getSonId());
            menuSonMapper.delete(dele);
            hmUserSon.getMenus().forEach(m -> {
                HmMenuSon menuSon = new HmMenuSon(upSon.getSonId(), m);
                menuSonMapper.insertSelective(menuSon);
            });
        }
        userSonMapper.updateByPrimaryKeySelective(upSon);
    }

    /**
     * 添加子账号日志记录
     */
    public void addUserSonLog(String oldValue, String newValue, String type, Integer sonId, UserLogin userLogin) {
        /*添加帐号时 添加日志记录*/
        try {
            HmUserSonLog logs = new HmUserSonLog();
            logs.setCreateUser(userLogin.getUsername());//创建人
            logs.setCreateId(userLogin.getUserId().toString());//创建人ID
            logs.setOldValue(oldValue);
            logs.setUserId(sonId);
            logs.setCreateIp(userLogin.getLoginIp());//IP
            logs.setCreateTime(new Date());//创建时间
            logs.setCreateType(type);
            logs.setNewValue(newValue);
            hmLogsService.addUserSonLog(logs);
        } catch (Exception e) {
            log.info("添加子账号日志记录报错", e);
        }
    }

    /**
     * 修改子账号记录
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @Override
    public int updatePassword(String oldPwd, String newPwd, UserLogin loginExt) {
        //验证旧密码==新密码
        ExceptionCast.castCheck(StrUtil.equals(oldPwd, newPwd), UcenterCode.UCENTER_NO_PWD);
        //验证密码格式
        String password = UserCk.checkPassword(newPwd);
        HmUserSon userSon = userSonMapper.selectByPrimaryKey(loginExt.getUserId());
        //验证就密码是否和数据库密码一致
        boolean matches = UserCk.matches(oldPwd, userSon.getPassword());
        ExceptionCast.castCheck(!matches, UcenterCode.UCENTER_OLDPASSWORD_ERROR);
        HmUserSon upSon = new HmUserSon();
        upSon.setPassword(password);
        upSon.setUpdated(new Date());
        upSon.setSonId(loginExt.getUserId());
        upSon.setResetPassword(0);
        log.info(userSon.getUsername() + "修改自己密码:" + oldPwd + "=>" + newPwd);
        this.addUserSonLog("******", "******", "修改子账号 密码", loginExt.getUserId(), loginExt);
        return userSonMapper.updateByPrimaryKeySelective(upSon);
    }

    public int updateStatus(String status, Integer sonId, UserLogin loginExt) {
        HmUserSon son = new HmUserSon();
        son.setSonId(sonId);
        son.setPrimaryId(loginExt.getPrimaryId());
        HmUserSon userSon = userSonMapper.selectOne(son);
        ExceptionCast.castCheck(userSon == null, UcenterCode.UCENTER_ACCOUNT_NOTEXISTS);
        HmUserSon upSon = new HmUserSon();
        upSon.setStatus(status);
        upSon.setSonId(sonId);
        if (status.equals("OPEN")) {
            this.addUserSonLog("停用", "启用", "修改子帐号 状态", sonId, loginExt);
        } else {
            this.addUserSonLog("启用", "停用", "修改子帐号 状态", sonId, loginExt);
        }
        return userSonMapper.updateByPrimaryKeySelective(upSon);
    }

    public List<String> selectSonIdByPrimary(Integer primaryId) {
        Example example = new Example(HmUserSon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("primaryId", primaryId);
        List<HmUserSon> hmUserSons = userSonMapper.selectByExample(example);
        List<String> sonids = new ArrayList<>();
        hmUserSons.forEach(hmUserSon -> {
            sonids.add(hmUserSon.getSonId().toString());
        });
        return sonids;
    }

    public void updateByPrimaryKeySelective(HmUserSon upUser) {
        userSonMapper.updateByPrimaryKeySelective(upUser);
    }
}
