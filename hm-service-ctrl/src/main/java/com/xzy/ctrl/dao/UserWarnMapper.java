package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.UserWarnDO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWarnMapper {
    List<UserWarnDO> listUserWarnDOs(Integer userId);
}
