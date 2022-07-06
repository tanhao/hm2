package com.xzy.logs.service.impl;

import com.github.pagehelper.PageHelper;
import com.xzy.logs.HmLogsService;
import com.xzy.logs.dao.HmLogsMapper;
import com.xzy.logs.dao.HmUserSonLogMapper;
import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.HmUserSonLog;
import com.xzy.pojo.logs.request.HmLogsRq;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.xzy.pojo.logs.response.hmLogsInfo;

@Transactional
@Slf4j
@DubboService(interfaceClass = HmLogsService.class)
public class HmLogsImpl implements HmLogsService {

    @Autowired
    HmLogsMapper mapper;

    @Autowired
    HmUserSonLogMapper hmUserSonLogMapper;

    public void  addHmLogs(HmLogs hmLogs){
        mapper.insert(hmLogs);
    }

    @Override
    public List<hmLogsInfo> getHmLogsList(HmLogsRq hmLogsRq) {
        PageHelper.startPage(hmLogsRq.getPage(), hmLogsRq.getSize());
        return mapper.getHmLogsList(hmLogsRq);
    }

    @Override
    public List<hmLogsInfo> selHmUserSonLogIdList(HmLogsRq hmLogsRq) {
        PageHelper.startPage(hmLogsRq.getPage(), hmLogsRq.getSize());
        return mapper.selHmUserSonLogIdList(hmLogsRq);
    }

    public void  addUserSonLog(HmUserSonLog hmLogs){
        hmUserSonLogMapper.insert(hmLogs);
    }

    /**
     * 批量添加
     * @param items
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer insertRecordLogs(List<HmLogs> items){
        return mapper.insertRecordLogs(items);
    }


}
