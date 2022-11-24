package org.lili.mq.rocketmq.dt;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TransactionConsumer {
    private static Logger logger = Logger.getLogger(TransactionConsumer.class.getClass());

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_consumer_group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTransaction", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            private Random random = new Random();

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : list) {
                    logger.info("消息消费者接收到消息 : " + msg);
                    logger.info("接收到的消息标签是 : " + new String(msg.getTags()));
                    logger.info("接收到的消息内容是 : " + new String(msg.getBody()));
                }
                try {
                    // 模拟业务处理
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
