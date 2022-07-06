package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserMaxdiffService;
import com.xzy.ctrl.dao.UserMaxdiffMapper;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.request.MaxdiffRQ;
import com.xzy.pojo.ctrl.vo.MaxdiffVO;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserMaxdiffServiceImpl extends UserCategoryServiceImpl implements UserMaxdiffService {

        @Autowired
        private UserMaxdiffMapper userMaxdiffMapper;
        @DubboReference
        private UserService userService;
            @Autowired
        private RedisTemplate redisTemplate;

        private final String USER_ZHUAN_REGRESS = "maxdiff:{}";
        private final String LOCK_USER_ZHUAN_REGRESS = "logck:maxdiff:{}";

    @Override
    public MaxdiffVO getMaxdiffVO(Integer userId) {
        MaxdiffVO maxdiffVO =this.getCategoryVO(new MaxdiffVO(),userId);
        return maxdiffVO;
    }

    @Override
    public MaxdiffRQ updateCompanyMaxdiff(MaxdiffRQ maxdiff) {
        if(maxdiff.getUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        log.info("JSON:"+JSON.toJSONString(maxdiff));
        maxdiff.setJson(JSON.toJSONString(maxdiff));
        userMaxdiffMapper.updateCompanyMaxdiff(maxdiff);
        return maxdiff;
    }


    @Override
    public String getRedisKey() {
        return USER_ZHUAN_REGRESS;
    }
    @Override
    public String getLockKey() {
        return LOCK_USER_ZHUAN_REGRESS;
    }

    @Override
    public <T extends UserKindDO> List<T> listUserKindDO(Integer userId) {
        return new ArrayList<>();
    }


    @Override
    public List<? extends UserCategoryDO> listUserCategoryDO(Integer userId) {
        return userMaxdiffMapper.listUserMaxdiffDOs(userId);
    }
}