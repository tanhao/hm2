package com.xzy.smart.service;

import com.xzy.member.TransactionService;
import com.xzy.pojo.mem.BetRq;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class SmartBetService {
    @DubboReference
    TransactionService transactionService;

    class Request {
        Integer userId;
        BetRq betRq;
        CompletableFuture<Map> future;
    }

    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>(2000);

    @Async
    public void queueBet(Integer planId, Integer userId, BetRq betRq) {
        try {
            Request request = new Request();
            request.userId = userId;
            request.betRq = betRq;
            CompletableFuture<Map> future = new CompletableFuture<>();
            request.future = future;
            queue.add(request);
            Map map = future.get();
            Integer code = (Integer) map.get("code");
            if (200 != code) {
                Thread.sleep(1000);
                Map check = transactionService.execQueueBet(request.userId, request.betRq);
                code = (Integer) check.get("code");
                if (200 != code) {
                    log.info("计划[{}]-投注失败[{}]", planId, check);
                }
            } else {
                log.info("计划[{}]-投注成功[{}]", planId, betRq.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void scheduledBet(){
        int size = queue.size();
        if (size == 0) return;
        Request request = queue.poll();
        Map map = new HashMap();
        try {
            map = transactionService.execQueueBet(request.userId, request.betRq);
        } catch (Exception e) {
            map.put("code", 9999);
        } finally {
            request.future.complete(map);
        }
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 4; i++) {
            scheduled.scheduleWithFixedDelay(() -> { scheduledBet(); }, 0, 10, TimeUnit.MILLISECONDS);
        }
    }
    /*
    @Async
    public void execBet(Integer planId,Integer userId, BetRq betRq, Integer indx) {
        if (indx > 5) {
            log.info("{}-投注5次失败[{}]",planId,betRq.toString());
            return;
        }
        try {
            Map<String, Object> res = transactionService.execQueueBet(userId, betRq);
            if (!res.get("code").equals(200)) {
                indx++;
                this.execBet(planId,userId, betRq, indx);
            }
        }catch (Exception e){
            indx++;
            this.execBet(planId,userId, betRq, indx);
        }
    }*/
}
