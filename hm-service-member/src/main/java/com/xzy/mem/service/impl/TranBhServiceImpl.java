package com.xzy.mem.service.impl;

import com.xzy.mem.dao.TranMapper;
import com.xzy.mem.service.TranBhService;
import com.xzy.pojo.mem.BetRp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@DubboService(interfaceClass = TranBhService.class)
public class TranBhServiceImpl implements TranBhService {
    @Autowired
    TranMapper tranMapper;

    @Override
    public void calculateStats(BetRp betRp) {
        try {
            //循环最多5次计算捕货
            for (int i = 0; i < 5; i++) {
                long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                //自动补货不需要投注参数，手动补货才需要添加
                betRp.setBetJsonArr("[]");
                tranMapper.calculateStats(betRp);
                long et = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                log.info("补货[{}]状态[{}]执行时间[{}]", betRp.getUniqueId(), betRp.getErrcode(), et - st);
                if (betRp.getErrcode() == 9999) {
                    Thread.sleep(500);
                    if (i == 5) {
                        log.info("未计算记录->" + betRp.toString());
                    }
                } else if (betRp.getErrcode() == 200) {
                    //成功结束循环捕货
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("未计算记录->" + betRp.toString());
        }
    }
}
