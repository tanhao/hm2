package com.xzy.ctrl;

import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.PlayDO;

import java.util.List;
import java.util.Map;

public interface PlayService {

    void savePlayToRedis();
    void delPlayForRedis();

    Map<Integer, PlayDO> mapPlays(Integer lotteryId);

     Map<Integer, List<PlayDO>> mapPlaysOfGroupId();


}