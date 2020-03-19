package org.study.mq.rocketMQ.order;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.mq.rocketMQ.spring.SpringProducer;

public class OrderProducerTest {

    private ApplicationContext container;

    @Before
    public void setup() {
        container = new ClassPathXmlApplicationContext("classpath:spring-producer-order.xml");
    }

    @Test
    public void sendMessage() throws Exception {
        OrderProducer producer = container.getBean(OrderProducer.class);
        OrderMessageQueueSelector messageQueueSelector = container.getBean(OrderMessageQueueSelector.class);

        String topicName = "topic_example_order";

        String[] statusNames = {"已创建", "已付款", "已配送", "已取消", "已完成"};

        //模拟订单消息
        for (int orderId = 1; orderId <= 10; orderId++) {
            //模拟订单的每个状态都发送消息
            for (int i = 0; i < statusNames.length; i++) {
                String messageContent = new OrderMessage().setId(orderId).setStatus(statusNames[i]).setSendOrder(i).setContent("Hello orderly rocketMQ message !").toString();

                Message sendMessage = new Message(
                        topicName,/* 消息主题 */
                        statusNames[i],/* 每个状态一个标签 */
                        orderId + "#" + statusNames[i],/* 自定义消息的 key ，常用于消息去重处理 */
                        messageContent.getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息内容 */
                );

                //发送消息并返回结果
                SendResult sendResult = producer.getProducer().send(sendMessage, messageQueueSelector, orderId);

                System.out.printf("%s %n", sendResult);
            }
        }
    }

}
