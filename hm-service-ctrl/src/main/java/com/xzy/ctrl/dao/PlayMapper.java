package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.PlayDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayMapper {
    List<PlayDO>  listPlays();
}
