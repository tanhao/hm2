package com.xzy.logs.dao;

import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.HmUserSonLog;
import com.xzy.pojo.logs.request.HmLogsRq;
import com.xzy.pojo.logs.response.hmLogsInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HmUserSonLogMapper extends Mapper<HmUserSonLog> {


}
