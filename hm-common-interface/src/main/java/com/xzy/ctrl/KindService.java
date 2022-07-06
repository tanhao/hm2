package com.xzy.ctrl;

import java.util.List;
import java.util.Map;

import com.xzy.pojo.ctrl.KindDO;

public interface KindService {

     void saveKindToRedis();

     void delKindForRedis();

     Map<Integer, KindDO> mapKinds();

     Map<Integer, List<KindDO>> mapKindsOfGroupId();

     Map<Integer,KindDO> mapKinds(Integer groupId);
}