package org.study.mq.activeMQ;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.transport.stomp.StompConnection;

public class StompProducer {

    public static void main(String[] args) throws Exception {
        StompConnection connection = new StompConnection();
        connection.open("localhost", 61613);
        //建立到代理服务器到连接
        connection.connect(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD);

        String message = "<a href=\"https://www.baidu.com\" target=\"_black\">微醺好时光，美酒3件7折，抢购猛戳</a>";
        connection.send("/topic/shopping-discount", message);

        connection.disconnect();
        connection.close();
    }
}
