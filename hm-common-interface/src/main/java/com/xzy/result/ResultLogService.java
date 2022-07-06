package com.xzy.result;

import com.xzy.pojo.result.HmResultLog;
import org.springframework.scheduling.annotation.Async;

@Async
public interface ResultLogService {
    void saveResultLog(HmResultLog hmResultLog);
}
