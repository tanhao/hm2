package com.xzy.result.service.impl;

import com.xzy.pojo.result.HmResultLog;
import com.xzy.result.ResultLogService;
import com.xzy.result.dao.HmResultLogMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(interfaceClass = ResultLogService.class)
public class ResultLogImpl implements ResultLogService {
    @Autowired
    HmResultLogMapper hmResultLogMapper;

    @Override
    public void saveResultLog(HmResultLog hmResultLog) {
        hmResultLogMapper.insertSelective(hmResultLog);

    }
}
