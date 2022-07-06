package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.QuickSettingService;
import com.xzy.ctrl.dao.QuickSettingMapper;
import com.xzy.pojo.ctrl.QuickSettingDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class QuickSettingServiceImpl implements QuickSettingService {

    private final String QUICK_SETTING = "quick_setting";

    @Autowired
    private QuickSettingMapper quickSettingMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<QuickSettingDO> listQuickSettings() {
        return quickSettingMapper.listQuickSettings();
    }

    @Override
    public void saveQuickSettingToRedis() {
        if (!redisTemplate.hasKey(QUICK_SETTING)) {
            List<QuickSettingDO> listQuickSettings = quickSettingMapper.listQuickSettings();
            listQuickSettings.forEach(qs -> {
                redisTemplate.boundZSetOps(QUICK_SETTING).incrementScore(qs, qs.getOrdering());
            });
        }
    }

    @Override
    public void delQuickSettingForRedis() {
        redisTemplate.delete(QUICK_SETTING);
    }

}
