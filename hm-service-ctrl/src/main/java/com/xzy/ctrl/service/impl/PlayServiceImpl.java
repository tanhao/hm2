package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.PlayService;
import com.xzy.ctrl.dao.PlayMapper;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.PlayDO;
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
public class PlayServiceImpl implements PlayService {

    private final String PLAY = "play";

    @Autowired
    private PlayMapper playMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void savePlayToRedis() {
        if (!redisTemplate.hasKey(PLAY)) {
            List<PlayDO> listPlays = playMapper.listPlays();
            listPlays.forEach(play -> redisTemplate.boundHashOps(PLAY)
                    .put(play.getPlayId().toString(), play));
        }
    }
    @Override
    public void delPlayForRedis(){
        redisTemplate.delete(PLAY);

    }
    @Override
    public Map<Integer, PlayDO> mapPlays(Integer lotteryId) {
        List<PlayDO> playDOs = redisTemplate.boundHashOps(PLAY).values();
        return playDOs.stream().filter(play->lotteryId.equals(play.getLotteryId())).collect(Collectors.toMap(PlayDO::getPlayId, Function.identity()));
    }


    @Override
    public Map<Integer, List<PlayDO>> mapPlaysOfGroupId() {
        List<PlayDO> playDOs = redisTemplate.boundHashOps(PLAY).values();
        Map<Integer, List<PlayDO>> plays = playDOs.stream().collect(Collectors.groupingBy(PlayDO::getLotteryId));
        for (List<PlayDO> item : plays.values()) {
            item.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
        }
        return plays;
    }


}