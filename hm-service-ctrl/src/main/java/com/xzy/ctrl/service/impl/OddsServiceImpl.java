package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.OddsService;
import com.xzy.ctrl.dao.OddsMapper;
import com.xzy.pojo.ctrl.OddsDO;
import com.xzy.pojo.ctrl.PlayDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class OddsServiceImpl implements OddsService {

    private final String ODDS = "odds";

    @Autowired
    private OddsMapper oddsMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void saveOddsToRedis() {
        if (!redisTemplate.hasKey(ODDS)) {
            List<OddsDO> listOddss = oddsMapper.listOddss();
            //listOddss.forEach(odds -> redisTemplate.boundHashOps(ODDS).put(odds.getOddsId().toString(), odds));
            Map<Integer, List<OddsDO>> mapOdds = listOddss.stream()
                    .collect(Collectors.groupingBy(OddsDO::getPlayId));
            for (Map.Entry<Integer, List<OddsDO>> entry : mapOdds.entrySet()) {
                redisTemplate.boundHashOps(ODDS).put(entry.getKey().toString(), entry.getValue());
            }
        }

    }

    @Override
    public void delOddsForRedis() {
        redisTemplate.delete(ODDS);
    }


    @Override
    public List<OddsDO> listOddss(Map<Integer, PlayDO> mapPlay){
        //List<List<OddsDO>> mapOdds= redisTemplate.boundHashOps(ODDS).values();
        System.out.println(mapPlay.keySet().stream().map(id->id.toString()).collect(Collectors.toList()));
        List<List<OddsDO>> oddsDOs = redisTemplate.boundHashOps(ODDS).multiGet(mapPlay.keySet().stream().map(id->id.toString()).collect(Collectors.toList()));
        List<OddsDO> logic=new ArrayList<>();
        oddsDOs.forEach(oddss->logic.addAll(oddss));
        return logic;
    }

}
