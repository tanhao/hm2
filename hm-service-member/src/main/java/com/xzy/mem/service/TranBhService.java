package com.xzy.mem.service;

import com.xzy.pojo.mem.BetRp;
import org.springframework.scheduling.annotation.Async;

public interface TranBhService {
    @Async
    void calculateStats( BetRp betRp);
}
