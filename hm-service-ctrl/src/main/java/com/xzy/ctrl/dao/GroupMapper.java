package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.GroupDO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper {
    List<GroupDO> listGroups();
}
