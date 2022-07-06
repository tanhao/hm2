package com.xzy.result.service;

import com.xzy.result.dao.ResultInitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

///队列多线程插入数据
public class InsertResultInitThread implements Runnable{

    final Logger log = LoggerFactory.getLogger(this.getClass());

    private ResultInitMapper resultInitMapper;

    private LinkedBlockingDeque<List> queues;

    public InsertResultInitThread(ResultInitMapper rlm , LinkedBlockingDeque<List> qu){
        this.resultInitMapper = rlm;
        this.queues = qu;
    }

    @Override
    public void run() {
        List event = queues.poll();
        try {
            resultInitMapper.batchUpdateResultInit(event);
        }catch(Exception e){
            log.error("出现异常："+e.getMessage(), e);
        }
    }
}
