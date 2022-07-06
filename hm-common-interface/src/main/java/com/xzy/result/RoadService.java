package com.xzy.result;

import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.request.ResultRq;
import com.xzy.pojo.result.vo.ResultCountVo;

import java.util.List;

public interface RoadService {
    JSONObject getRoadInfo(Integer lotteryId);

}
