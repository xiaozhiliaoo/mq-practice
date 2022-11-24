package org.lii.mq.rabbitmq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class FooMessageListener implements MessageListener {

    public void onMessage(Message message) {
        String messageBody = new String(message.getBody());
        System.out.println("接收到消息 '" + messageBody + "'");
    }
}
