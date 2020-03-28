package org.study.mq.myMq.activeMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueProducer {

    /**
     * 默认用户名
     */
    public static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    /**
     * 默认密码
     */
    public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    /**
     * 默认连接地址
     */
    public static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
        try {
            //创建连接
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建 Queue，需指定其对应的队列名称，消息生产者和消费者将根据它来发送、接收对应的消息。
            Queue myTestQueue = session.createQueue("activemq-queue-test1");
            //消息生产者
            MessageProducer producer = session.createProducer(myTestQueue);
            //创建一条消息对象
            TextMessage message = session.createTextMessage("测试点对点的一条消息333");
            //发送一条消息
            producer.send(message);
            //事务提交
            session.commit();

            //关闭资源
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
