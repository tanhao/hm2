package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.KindService;
import com.xzy.ctrl.dao.KindMapper;
import com.xzy.pojo.ctrl.KindDO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class KindServiceImpl implements KindService {

    private final String KIND = "kind";

    @Autowired
    private KindMapper kindMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveKindToRedis() {
        if (!redisTemplate.hasKey(KIND)) {
            final List<KindDO> listKinds = kindMapper.listKinds();
            listKinds.forEach(kind -> redisTemplate.boundHashOps(KIND).put(kind.getKindId().toString(), kind));
        }
    }
    @Override
    public void delKindForRedis() {
        redisTemplate.delete(KIND);
    }

    @Override
    public Map<Integer, KindDO> mapKinds() {
        List<KindDO> kindDOs = redisTemplate.boundHashOps(KIND).values();
        return kindDOs.stream().collect(Collectors.toMap(KindDO::getKindId, Function.identity()));
    }

    @Override
    public Map<Integer, List<KindDO>> mapKindsOfGroupId() {
        List<KindDO> kindDOs = redisTemplate.boundHashOps(KIND).values();
        Map<Integer, List<KindDO>> kinds = kindDOs.stream().collect(Collectors.groupingBy(KindDO::getGroupId));
        for (List<KindDO> item : kinds.values()) {
            item.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
        }
        return kinds;
    }

    @Override
    public Map<Integer,KindDO> mapKinds(Integer groupId) {
        List<KindDO> kindDOs = redisTemplate.boundHashOps(KIND).values();
        Map<Integer,KindDO> map=kindDOs.stream().filter(kind->groupId.equals(kind.getGroupId())).collect(Collectors.toMap(KindDO::getKindId, Function.identity()));
        return map;
    }

}
