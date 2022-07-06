package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.GroupService;
import com.xzy.ctrl.dao.GroupMapper;
import com.xzy.pojo.ctrl.GroupDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class GroupServiceImpl implements GroupService {

    private final String GROUP = "group";

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveGroupToRedis() {
        if (!redisTemplate.hasKey(GROUP)) {
            List<GroupDO> listGroups = groupMapper.listGroups();
            listGroups.forEach(group -> {
                redisTemplate.boundHashOps(GROUP).put(group.getGroupId().toString(), group);
            });

        }
    }
    @Override
    public void delGroupForRedis(){
        redisTemplate.delete(GROUP);
    }

    @Override
    public List<GroupDO> listGroups() {
        List<GroupDO> groups = redisTemplate.boundHashOps(GROUP).values();
        groups.sort((a1, a2) -> a1.getOrdering() - a2.getOrdering());
        return groups;

    }

    @Override
    public Map<Integer, GroupDO> mapGroups() {
        return this.listGroups().stream().collect(Collectors.toMap(GroupDO::getGroupId, Function.identity()));
    }

}
