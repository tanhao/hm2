package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.GroupService;
import com.xzy.ctrl.KindService;
import com.xzy.ctrl.LotteryService;
import com.xzy.ctrl.UserLotteryService;
import com.xzy.pojo.ctrl.GroupDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.UserKindDO;
import com.xzy.pojo.ctrl.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
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
@Slf4j
public abstract class UserKindServiceImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    @DubboReference
    private GroupService groupService;

    @DubboReference
    private KindService kindService;

    @DubboReference
    private LotteryService lotteryService;

    @DubboReference
    private UserLotteryService userLotteryService;
    @Autowired
    private RedisLock redisLock;

    public <T extends KindVO> T getKindVO(T t, Integer userId) {
        List<GroupDO> listGroups = groupService.listGroups();
        Map<Integer, List<KindDO>> mapKinds = kindService.mapKindsOfGroupId();
        List<LotteryVO> listLotterys = userLotteryService.listLotteryVOs(userId);

        if (t instanceof BuhuoVO) {
            mapKinds.values().forEach(kinds -> kinds.forEach(kind -> {
                this.JsonIgnoreBuhuo(kind);
            }));
        } else if (t instanceof JumpVO) {
            mapKinds.values().forEach(kinds -> kinds.forEach(kind -> {
                this.JsonIgnoreJump(kind);
            }));
        }else if(t instanceof ZhuanRegressVO){
            mapKinds.values().forEach(kinds -> kinds.forEach(kind -> {
                this.JsonIgnoreZhuanRegress(kind);
            }));
        } else {
            mapKinds.values().forEach(kinds -> kinds.forEach(kind -> {
                this.JsonIgnoreOther(kind);
            }));
        }

        listGroups.forEach(group -> {
            this.JsonIgnoreGroup(group);
        });


        Map<Integer, List<LotteryVO>> mapLotterys = listLotterys.stream()
                .collect(Collectors.groupingBy(LotteryVO::getGroupId));
        for (List<LotteryVO> item : mapLotterys.values()) {
            item.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
        }
        mapLotterys.values().forEach(tiems -> tiems.forEach(lottery -> {
            this.JsonIgnoreLottery(lottery);
        }));

        t.setGroups(listGroups);
        t.setKinds(mapKinds);
        t.setLotterys(mapLotterys);

        Map<Integer,? extends  Map<Integer,UserKindDO>> kinds = this.listKindDOs(userId).stream().collect(Collectors.groupingBy(UserKindDO::getLotteryId,Collectors.toMap(item -> item.getKindId(), Function.identity())));
        kinds.values().forEach(map->map.values().forEach(item->{
            item.setId(null);
            item.setUserId(null);
            item.setKindId(null);
            item.setLotteryId(null);
        }));

        t.setUserKinds(kinds);
        return t;
    }

    public <T extends UserKindDO>  List<T> listKindDOs(Integer userId){
            /*
            String redisKey = StrUtil.format(this.getRedisKey(), userId);
            List<T> list = redisTemplate.boundHashOps(redisKey).values();
            if (list.size() == 0) {
                list = this.listUserKindDO(userId);
                if(list.size()>0){
                    String lockKey= StrUtil.format(this.getLockKey(), userId);
                    String lockValue= UUID.randomUUID().toString();
                    boolean isLock=redisLock.lock(lockKey,lockValue);
                    if(!isLock) {ExceptionCast.cast("获取锁失败，请稍后再试！");}
                    if(!redisTemplate.hasKey(redisKey)) {
                        list.forEach(item -> redisTemplate.boundHashOps(redisKey).put(item.getId().toString(), item));
                    }
                    redisLock.unlock(lockKey,lockValue);
                }
            }
            return list;

             */
        List<T> list = this.listUserKindDO(userId);
        return list;
    }


    public abstract String getRedisKey();
    public abstract String getLockKey();
    public abstract <T extends UserKindDO> List<T> listUserKindDO(Integer userId);

    private void JsonIgnoreGroup(GroupDO group) {
        group.setGroupKey(null);
        group.setOrdering(null);
    }

    private void JsonIgnoreBuhuo(KindDO kind) {
        kind.setGroupId(null);
        kind.setKindKey(null);
        kind.setQuickSettingId(null);
        kind.setIsCljp(null);
        kind.setDigit(null);
        kind.setIsBuhuo(null);
        kind.setOrdering(null);
    }

    private void JsonIgnoreJump(KindDO kind) {
        kind.setGroupId(null);
        kind.setKindKey(null);
        kind.setQuickSettingId(null);
        kind.setIsCljp(null);
        kind.setBuhuoType(null);
        kind.setIsBuhuo(null);
        kind.setOrdering(null);
    }
    private void JsonIgnoreZhuanRegress(KindDO kind) {
        kind.setGroupId(null);
        kind.setKindKey(null);
        kind.setIsCljp(null);
        kind.setBuhuoType(null);
        kind.setIsBuhuo(null);
        kind.setOrdering(null);
    }

    private void JsonIgnoreOther(KindDO kind) {
        kind.setGroupId(null);
        kind.setKindKey(null);
        kind.setQuickSettingId(null);
        kind.setIsCljp(null);
        kind.setBuhuoType(null);
        kind.setIsBuhuo(null);
        kind.setDigit(null);
        kind.setOrdering(null);
    }

    private void JsonIgnoreLottery(LotteryVO lottery) {
        lottery.setGroupId(null);
        lottery.setUserId(null);
        lottery.setLotteryKey(null);
        lottery.setDelayOpen(null);
        lottery.setPrecedeClose(null);
        lottery.setZmPrecedeClose(null);
        lottery.setIsOpenSelf(null);
        lottery.setIsLink(null);
        lottery.setIsReset(null);
        lottery.setIsOpenUp(null);
        lottery.setOrdering(null);
        lottery.setCodeNums(null);
        lottery.setResultSize(null);
        lottery.setOpenTime(null);
        lottery.setActionTime(null);
        lottery.setGameNo(null);
    }

}
