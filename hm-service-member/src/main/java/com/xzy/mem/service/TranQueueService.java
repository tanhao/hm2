package com.xzy.mem.service;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.MemberCode;
import com.xzy.mem.service.impl.TransactionImpl;
import com.xzy.pojo.mem.BetRq;
import com.xzy.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
@Slf4j
public class TranQueueService {
    class Request {
        Integer userId;
        BetRq betRq;
        CompletableFuture<Map> future;
    }

    @Autowired
    TransactionImpl transactionService;

    LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>(1000);

    public void tranQueueBet(Integer userId, BetRq betRq) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.userId = userId;
        request.betRq = betRq;
        CompletableFuture<Map> future = new CompletableFuture<>();
        request.future = future;
        queue.add(request);
        Map map = future.get();
        Integer code = (Integer) map.get("code");
        if (200 != code) {
            ExceptionCast.castData(CodeUtils.getByCode(MemberCode.class, code), map.get("keys"));
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
        ScheduledExecutorService scheduled =  Executors.newScheduledThreadPool(20);
        scheduled.scheduleAtFixedRate(() -> { scheduledBet(); }, 0, 10, TimeUnit.MILLISECONDS);
        scheduled.scheduleAtFixedRate(() -> { scheduledBet(); }, 0, 10, TimeUnit.MILLISECONDS);
    }

}
