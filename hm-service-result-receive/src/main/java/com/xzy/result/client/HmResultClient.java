package com.xzy.result.client;

import com.alibaba.fastjson.JSONObject;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.result.ResultReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
//@Component
//@ClientEndpoint
public class HmResultClient {
    @DubboReference
    ResultReceiveService resultReceiveService;

    public HmResultClient() {
    }

    public HmResultClient(ResultReceiveService resultReceiveService) {
        this.resultReceiveService = resultReceiveService;
    }

//        public static String wsUrl = "ws://localhost:23330/websocket/link/result";
    public static String wsUrl = "ws://103.244.55.46:23330/websocket/link/result";
    //    public static String wsUrl = "ws://result.hm928.co/websocket/link/result";
    public Session session;

    /**
     * 开始连接服务端
     *
     * @param session
     */
    @OnOpen
    public void open(Session session) {
        log.info("Client WebSocket is opening...");
        this.session = session;
    }

    /**
     * 服务端返回数据
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            ResultGrab resultGrab = JSONObject.parseObject(message, ResultGrab.class);
            resultGrab.setUpdateBy("socket");
            resultGrab.setAutoPayout(Boolean.FALSE);
            //先进行保存赛果操作
//            Integer i = resultReceiveService.saveResult(resultGrab);
//            if(i.equals(200)) {
//                resultReceiveService.sendResult(resultGrab);
//                resultReceiveService.spPayoutPrize(resultGrab);
//            }
        } catch (Exception e) {

        }
    }

    /**
     * 服务端关闭，等待连接
     */
    @OnClose
    public void onClose() {
        log.info("Websocket closed");
        this.startSocket();
    }

    /**
     * 发送信息
     *
     * @param message
     */
    public void send(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 启动socket连接
     */
    public void startSocket() {
        if (this.session == null || !this.session.isOpen()) {
            try {
                Thread.sleep(10000);
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, new URI(wsUrl));
            } catch (Exception i) {
                //i.printStackTrace();
            }
            startSocket();
        }
    }

    class startRun implements Runnable {
        ResultReceiveService service;

        public startRun(ResultReceiveService service) {
            this.service = service;
        }

        @Override
        public void run() {
            HmResultClient client = new HmResultClient(service);
            client.startSocket();
        }
    }

    @PostConstruct
    public void initClient() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new startRun(resultReceiveService));
        executorService.shutdown();
    }
}