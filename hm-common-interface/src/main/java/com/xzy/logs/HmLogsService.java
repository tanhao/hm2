package com.xzy.logs;

import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.HmUserSonLog;
import com.xzy.pojo.logs.request.HmLogsRq;
import com.xzy.pojo.logs.response.hmLogsInfo;

import java.util.List;

public interface HmLogsService {
    public void  addHmLogs(HmLogs hmLogs);

    public void  addUserSonLog(HmUserSonLog hmLogs);

    List<hmLogsInfo> getHmLogsList(HmLogsRq hmLogsRq);
    List<hmLogsInfo> selHmUserSonLogIdList(HmLogsRq hmLogsRq);


    public Integer insertRecordLogs(List<HmLogs> items);
}
