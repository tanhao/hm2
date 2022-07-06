package com.xzy.logs;

import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.logs.request.HmLogsRq;
import com.xzy.pojo.logs.response.hmLogsInfo;

import java.util.List;

public interface IpaddressService {
    List<IpAddress> selectByIps(String[] ips);
}
