package com.xzy.socket;

import com.xzy.pojo.tio.entity.SendInfo;

public interface TioSocketService {
    /**
     * 给所有连接账号发送数据
     *
     * @param sendInfo
     */
    void sendToAll(SendInfo sendInfo);

    /**
     * 给指定id账号发送数据
     *
     * @param userId
     * @param sendInfo
     */
    void sendToUserId(Integer userId, SendInfo sendInfo);

    /**
     * 给指定token账号发送数据
     *
     * @param token
     * @param sendInfo
     */
    void sendToToken(String token, SendInfo sendInfo);

    /**
     * 给自己所有下线代理发送数据
     * @param userId
     * @param sendInfo
     */
    void sendToSelfAgent(Integer userId, SendInfo sendInfo);

    /**
     * 给自己所有下线所有会员发送数据
     * @param userId
     * @param sendInfo
     */
    void sendToSelfMember(Integer userId, SendInfo sendInfo);

    /**
     * 给自己所有下线账号发送数据
     * @param userId
     * @param sendInfo
     */
    void sendToSelfAll(Integer userId, SendInfo sendInfo);
    /**
     * 给自己所有下线账号发送数据
     * @param group
     * @param sendInfo
     */
     void sendToGroup(String group, SendInfo sendInfo);
}
