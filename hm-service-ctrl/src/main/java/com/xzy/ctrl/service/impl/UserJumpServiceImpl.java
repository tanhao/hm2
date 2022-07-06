package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserJumpService;
import com.xzy.ctrl.dao.UserJumpMapper;
import com.xzy.enums.EJumpType;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.request.JumpRQ;
import com.xzy.pojo.ctrl.vo.JumpVO;
import com.xzy.pojo.user.HmUser;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 跳盘业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class UserJumpServiceImpl extends UserKindServiceImpl implements UserJumpService {

    @Autowired
    private UserJumpMapper userJumpMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private UserService userService;

    private final String USER_JUMP = "jump:{}";
    private final String LOCK_USER_JUMP = "logck:jump:{}";

    @Override
    public JumpVO getJumpVO(Integer userId) {
        JumpVO jumpVO =this.getKindVO(new JumpVO(),userId);
        HmUser user=userService.findById(userId);
        jumpVO.setJumpType(EJumpType.valueOf(user.getJumpType()==null?"XZ":user.getJumpType().name()));
        return jumpVO;
    }
    @Override
    public JumpRQ updateJump(JumpRQ jump) {
        if(jump.getUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        log.info("JSON:"+ JSON.toJSONString(jump));
        jump.setJson(JSON.toJSONString(jump));
        userJumpMapper.updateJump(jump);
        return jump;
    }

    @Override
    public String getRedisKey() {
        return USER_JUMP;
    }
    @Override
    public String getLockKey() {
        return LOCK_USER_JUMP;
    }
    @Override
    public List<? extends UserKindDO> listUserKindDO(Integer userId) {
        return userJumpMapper.listUserJumpDOs(userId);
    }
}
