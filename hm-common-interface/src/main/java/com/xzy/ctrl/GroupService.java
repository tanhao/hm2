package com.xzy.ctrl;

import java.util.List;
import java.util.Map;

import com.xzy.pojo.ctrl.GroupDO;

public interface GroupService {

    public void delGroupForRedis();

    public void saveGroupToRedis();

    public Map<Integer, GroupDO> mapGroups();

    public List<GroupDO> listGroups();
}