package com.xzy.result.mq.result;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.result.ResultReceiveService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class ResultMessageListener implements MessageOrderListener {
    @DubboReference
    ResultReceiveService resultReceiveService;

    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        try {
            ResultGrab resultGrab = JSONObject.parseObject(new String(message.getBody()), ResultGrab.class);
            resultGrab.setUpdateBy("socket_mq_listener");
            resultGrab.setAutoPayout(Boolean.FALSE);
            //先进行保存赛果操作
            Integer i = resultReceiveService.saveResult(resultGrab);
            if (i.equals(200)) {
                resultReceiveService.sendResult(resultGrab);
                resultReceiveService.spPayoutPrize(resultGrab);
            }
            return OrderAction.Success;
        } catch (Exception e) {
            //消费失败，挂起当前队列
            return OrderAction.Suspend;
        }
    }
}
