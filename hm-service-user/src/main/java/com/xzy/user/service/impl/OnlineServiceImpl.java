package com.xzy.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.user.HmUser;
import com.xzy.pojo.user.ext.OnlineNum;
import com.xzy.pojo.user.request.UserLogRq;
import com.xzy.user.OnlineService;
import com.xzy.user.dao.UserMapper;
import com.xzy.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import com.xzy.logs.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@DubboService(interfaceClass = OnlineService.class)
public class OnlineServiceImpl implements OnlineService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private UserMapper userMapper;


    public List<UserLogRq> getOnlineAll(Integer userId, Boolean admin,String type) {
        List list= loginUtil.selectTokenList();
        List<UserLogRq> listULE = new ArrayList<>();
        String name="";
        for (Object item : list) {
            UserLogRq user = JSON.parseObject(item.toString(), UserLogRq.class);
            if(type.equals("company") ){//公司
                if(user.getUserLevel()!=2){
                    continue;
                }
            }else if(type.equals("member")){
                if(!user.getUserType().equals("MEMBER")){
                    continue;
                }
            }else if(type.equals("agent")){
                if(!user.getUserType().equals("AGENT") || user.getUserLevel()<=2 ){
                    continue;
                }
            }else if(type.equals("subAccount")){
                if(!user.getEnabledSon()){
                    continue;
                }
            }
            String parentPath = user.getParentPath();
            Integer primaryId = user.getPrimaryId();
            boolean status = parentPath.contains(","+userId.toString()+",");
            if (admin) {
                listULE.add(user);
            } else {
                if (status ||  (primaryId.equals(userId) && !user.getUserId().equals(userId))) {
                    listULE.add(user);
                }
            }
        }
        return listULE;
    }

    public boolean deleteIdOnline(String id) {
        return loginUtil.delTokenById(new String[]{id});
    }


    public boolean outOnlineAll(Integer userId, Boolean admin,String type){
        List list= loginUtil.selectTokenList();
        for (Object item : list) {
            UserLogRq user = JSON.parseObject(item.toString(), UserLogRq.class);
            String parentPath = user.getParentPath();
            Integer primaryId = user.getPrimaryId();
            boolean status = parentPath.contains(","+userId.toString()+",");
            if (admin) {
                this.getType(type,user);
            } else {
                if (status ||  (primaryId.equals(userId) && !user.getUserId().equals(userId))) {
                    this.getType(type,user);
                }
            }
        }
        return true; //loginUtil.delTokenById(new String[]{listULE});
    }

    /**
     * 验证 类型
     * @param type
     * @param user
     */
    public void getType(String type,UserLogRq user){
        if(type.equals("company") ){//公司
            if(user.getUserLevel()==2){
                loginUtil.delTokenById(new String[]{user.getUserId().toString()});
            }
        }else if(type.equals("member")){
            if(user.getUserType().equals("MEMBER")){
                loginUtil.delTokenById(new String[]{user.getUserId().toString()});
            }
        }else if(type.equals("agent")){
            if(user.getUserType().equals("AGENT") || user.getUserLevel()>2 ){
                loginUtil.delTokenById(new String[]{user.getUserId().toString()});
            }
        }else if(type.equals("subAccount")){
            if(user.getEnabledSon()){
                loginUtil.delTokenById(new String[]{user.getUserId().toString()});
            }
        }else {
            loginUtil.delTokenById(new String[]{user.getUserId().toString()});
        }
    }

    /**
     * 统计在线人数
     * @param userId
     * @return
     */
    public OnlineNum getOnlineNum(Integer userId) {
        OnlineNum onlineNum = new OnlineNum();
        List list = loginUtil.selectTokenList();
        HmUser user = userMapper.selectByPrimaryKey(userId);
        onlineNum.setLoginLvl(user.getUserLevel());
        int agent = 0;
        int company = 0;
        int member = 0;
        int sub = 0;
        for (Object item : list) {
            UserLogRq userLoginExt = JSON.parseObject(item.toString(), UserLogRq.class);
            if (userLoginExt.getEnabledSon() && userLoginExt.getPrimaryId().equals(userId)) {
                sub++;
            }
            if (userLoginExt.getUserId() != userId && userLoginExt.getUserLevel() > user.getUserLevel() && userLoginExt.getParentPath().contains(userId.toString())) {
                if ("AGENT".equals(userLoginExt.getUserType()) || "agent".equals(userLoginExt.getUserType())) {
                    if (userLoginExt.getUserLevel() == 2) {
                        company++;
                    } else {
                        agent++;
                    }
                } else if ("MEMBER".equals(userLoginExt.getUserType()) || "member".equals(userLoginExt.getUserType())) {
                    member++;
                }
            }
        }
        onlineNum.setAgent(agent);
        onlineNum.setCompany(company);
        onlineNum.setMember(member);
        onlineNum.setSub(sub);
        return onlineNum;
    }

}
