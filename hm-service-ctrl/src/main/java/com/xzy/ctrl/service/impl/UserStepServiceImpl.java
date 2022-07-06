package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.UserStepService;
import com.xzy.ctrl.dao.UserStepMapper;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.UserStepDO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.StepVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 即时注单调赔步长业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
@Slf4j
public class UserStepServiceImpl extends UserCategoryServiceImpl implements UserStepService {

    @Autowired
    private UserStepMapper userStepMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;


    private final String USER_STEP = "step:{}";
    private final String LOCK_USER_STEP = "logck:step:{}";

    @Override
    public StepVO getStepVO(Integer userId) {
        StepVO stepVO = this.getCategoryVO( new StepVO(),userId);
        return stepVO;
    }

    @Override
    public List<UserStepDO> listUserStepDOsByLotteryId(Integer userId, Integer lotteryId) {
        return userStepMapper.listUserStepDOsByLotteryId( userId,lotteryId);
    }

    @Override
    public OddsRegressLimitFulloddsRQ updateStep(OddsRegressLimitFulloddsRQ orlf) {
            if(orlf.getUserId()==null){
                ExceptionCast.cast(CtrlCode.USER_ID_NULL);
            }
            log.info("JSON:"+ JSON.toJSONString(orlf));
            orlf.setJson(JSON.toJSONString(orlf));
            userStepMapper.updateStep(orlf);
            return orlf;
    }

    @Override
    public String getRedisKey() {
        return USER_STEP;
    }

    @Override
    public String getLockKey() {
        return LOCK_USER_STEP;
    }

    @Override
    public <T extends UserKindDO> List<T> listUserKindDO(Integer userId) {
        return new ArrayList<>();
    }

    @Override
    public List<? extends UserCategoryDO> listUserCategoryDO(Integer userId) {
        return userStepMapper.listUserStepDOs(userId);
    }

}
