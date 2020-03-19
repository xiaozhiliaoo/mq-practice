package org.study.mq.rocketMQ.order;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderMessageQueueSelector implements MessageQueueSelector {

    @Override
    public MessageQueue select(List<MessageQueue> list, Message message, Object orderId) {
        Integer id = (Integer)orderId;

        return list.get(id % list.size());
    }

}
