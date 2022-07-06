package com.xzy.ctrl.service.impl;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.ctrl.QuickSettingService;
import com.xzy.ctrl.UserZhuanRegressService;
import com.xzy.ctrl.dao.UserRegressMapper;
import com.xzy.pojo.ctrl.QuickSettingDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanRegressVO;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
@SuppressWarnings("all")
public class UserZhuanRegressServiceImpl extends UserKindServiceImpl implements UserZhuanRegressService {

        @Autowired
        private UserRegressMapper userRegressMapper;
        @DubboReference
        private UserService userService;
        @DubboReference
        private QuickSettingService quickSettingService;
        @Autowired
        private RedisTemplate redisTemplate;


        private final String USER_Zhuan_REGRESS = "zhuan_regress:{}";
        private final String LOCK_USER_Zhuan_REGRESS = "logck:zhuan_regress:{}";

        @Override
        public ZhuanRegressVO getZhuanRegressVO(Integer userId) {
            ZhuanRegressVO zhuanRegressVO =this.getKindVO(new ZhuanRegressVO(),userId);
            UserInfo user=userService.findBaseById(userId);
            Boolean hasOrder=userService.countOrder(userId);
            Map<String,Boolean> markets= Arrays.asList(user.getMarketOpen().split(",")).stream().collect(Collectors.toMap(item->item, item->true));
            List<QuickSettingDO> quickSetting= quickSettingService.listQuickSettings();

            zhuanRegressVO.setMarkets(markets);
            zhuanRegressVO.setHasOrder(hasOrder);
            zhuanRegressVO.setQuickSetting(quickSetting);
            return zhuanRegressVO;
        }

    @Override
    public OddsRegressLimitFulloddsRQ updateZhuanRegress(OddsRegressLimitFulloddsRQ orlf) {
        if(orlf.getUserId()==null){
            ExceptionCast.cast(CtrlCode.USER_ID_NULL);
        }
        log.info("JSON:"+JSON.toJSONString(orlf));
        orlf.setJson(JSON.toJSONString(orlf));
        userRegressMapper.updateZhuanRegress(orlf);
        return orlf;
    }

    @Override
    public String getRedisKey() {
        return USER_Zhuan_REGRESS;
    }
    @Override
    public String getLockKey() {
        return LOCK_USER_Zhuan_REGRESS;
    }
    @Override
    public List<? extends UserKindDO> listUserKindDO(Integer userId) {
        return userRegressMapper.listZhuanRegress(userId);
    }
}