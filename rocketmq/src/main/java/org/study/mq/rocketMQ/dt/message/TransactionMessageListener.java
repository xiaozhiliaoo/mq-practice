package org.study.mq.rocketMQ.dt.message;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.study.mq.rocketMQ.dt.model.Point;
import org.study.mq.rocketMQ.dt.model.UserPointMessage;
import org.study.mq.rocketMQ.dt.service.PointService;

import javax.annotation.Resource;
import java.util.List;

public class TransactionMessageListener implements MessageListenerConcurrently {

    private Logger logger = Logger.getLogger(getClass());

    @Resource
    private PointService pointService;

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt message : list) {
                logger.info("消息消费者接收到消息 : " + message);
                logger.info("接收到的消息内容是 : " + new String(message.getBody()));

                //从消息体中获取积分消息对象
                UserPointMessage pointMessage = JSON.parseObject(message.getBody(), UserPointMessage.class);
                if (pointMessage != null) {
                    Point point = new Point();
                    point.setUserId(pointMessage.getUserId());
                    point.setAmount(pointMessage.getAmount());
                    //保存用户积分记录并提交本地事务
                    pointService.savePoint(point);
                }
            }

        } catch (Exception e) {
            logger.error("消费消息时报错", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        //正常执行就返回消息消费成功
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
