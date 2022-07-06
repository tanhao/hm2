package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.QuickSettingDO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuickSettingMapper {
    List<QuickSettingDO> listQuickSettings();
}
