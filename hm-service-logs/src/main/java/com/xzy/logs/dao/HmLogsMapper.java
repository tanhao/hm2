package com.xzy.logs.dao;

import com.xzy.pojo.logs.response.hmLogsInfo;
import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.request.HmLogsRq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HmLogsMapper extends Mapper<HmLogs> {

    List<hmLogsInfo> getHmLogsList(HmLogsRq hmLogsRq);

    List<hmLogsInfo> selHmUserSonLogIdList(HmLogsRq hmLogsRq);
    /**
     * 批量插入日志操作
     * @param recordLogs
     * @return
     */
    Integer insertRecordLogs(@Param("items") List<HmLogs> recordLogs);
}
