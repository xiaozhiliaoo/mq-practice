package org.study.mq.rabbitMQ.java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class StompProducer {

    public static void main(String[] args) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置 RabbitMQ 地址
        factory.setHost("localhost");
        factory.setVirtualHost("/");
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //创建信道
        Channel channel = conn.createChannel();

        String exchangeName = "exchange-stomp";
        channel.exchangeDeclare(exchangeName, "topic");
        String routingKey = "shopping.discount";

        String message = "<a href=\"https://www.baidu.com\" target=\"_black\">微醺好时光，美酒3件7折，抢购猛戳</a>";
        //发布消息
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());

        channel.close();
        conn.close();
    }
}
