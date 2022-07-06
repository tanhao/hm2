package com.xzy.pojo.tio.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendInfo implements Serializable {

    /**
     * 发送信息的代码
     */
    private String code;

    /**
     * 消息内容
     */
    private Object content;

    /**
     * 服务端时间戳毫秒数
     */
    private long serverTime=System.currentTimeMillis();

    public SendInfo(String code, Object content) {
        this.code = code;
        this.content = content;
    }
}
