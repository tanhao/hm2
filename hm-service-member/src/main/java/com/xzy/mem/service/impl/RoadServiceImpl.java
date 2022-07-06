package com.xzy.mem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xzy.mem.dao.RoadMapper;
import com.xzy.mem.utils.LotteryUtils;
import com.xzy.mem.utils.RoadUtils;
import com.xzy.result.RoadService;
import com.xzy.utils.DateUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@DubboService
public class RoadServiceImpl implements RoadService {
    @Autowired
    private RoadMapper roadMapper;


    /**
     * 获取路纸信息
     *@auther yzy
     *@date 2019/8/12 11:20
     */
    public JSONObject getRoadInfo(Integer lotteryId){

        String groupKey = roadMapper.getGroupKey(lotteryId);

        List list = roadMapper.getResultList(lotteryId,DateUtil.getAccountDay().toString());
        if(groupKey.equals(LotteryUtils.GROUP_PK10)){
            return RoadUtils.createPk10RoadInfo(list);
        }else if(groupKey.equals(LotteryUtils.GROUP_SSC)){
            return RoadUtils.createSscRoadInfo(list);
        }else if(groupKey.equals(LotteryUtils.GROUP_HAPPY10)){
            return RoadUtils.createGDKL10RoadInfo(list);
        }
        return new JSONObject();
    }
}
