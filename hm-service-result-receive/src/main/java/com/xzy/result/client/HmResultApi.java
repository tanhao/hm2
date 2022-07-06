package com.xzy.result.client;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.HmResultCode;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HmResultApi {
    @Autowired
    RestTemplate restTemplate;
    private String apiUrl = "http://result.678kai.co/api/";

    public String findPeriodList(String lotteryKey, List<String> gameNos) {
        Map<String, String> params = new HashMap();
        params.put("gameNos", StringUtils.join(gameNos, ","));
        String url = String.join("", apiUrl, "list/period/", lotteryKey, "?gameNos={gameNos}");
        String rs = restTemplate.getForObject(url, String.class, params);
        return rs;
    }

    public String findPeriod(String lotteryKey, String gameNo) {
        String url = String.join("", apiUrl, "period/", lotteryKey, "/", gameNo);
        JSONObject rs = restTemplate.getForObject(url, JSONObject.class);
        if ((Boolean) rs.get("success")) {
            ResultGrab resultGrab = JSON.parseObject(rs.getJSONObject("data").toJSONString(), ResultGrab.class);
            if (StrUtil.isBlank(resultGrab.getResult())) {
                ExceptionCast.cast(HmResultCode.NOT_FOUND_RESULT);
            }
            return resultGrab.getResult();
        }
        return "";
    }

    public String findPeriods(String lotteryKey, String day) {
        String url = String.join("", apiUrl, "periods/", lotteryKey, "/", day);
        String rs = restTemplate.getForObject(url, String.class);
        return rs;
    }

    public String findNewResult() {
        String url = String.join("", apiUrl, "new/result");
        String rs = restTemplate.getForObject(url, String.class);
        return rs;
    }

}
