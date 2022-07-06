package com.xzy.ctrl;

import java.util.List;

import com.xzy.pojo.ctrl.QuickSettingDO;

public interface QuickSettingService {
    public List<QuickSettingDO> listQuickSettings();

    public void saveQuickSettingToRedis();

    void delQuickSettingForRedis();
}