package org.lili.mq.mymq.activeMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {

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
            //开启连接
            connection.start();
            //创建会话
            final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建 Queue，用作消费者消费消息的目的地
            Queue myTestQueue = session.createQueue("activemq-queue-test1");
            //消息消费者
            MessageConsumer consumer = session.createConsumer(myTestQueue);
            //消费者实现监听接口消费信息
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(textMessage.getText());
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                }
            });

            //让主线程休眠100秒，使消息消费者对象能继续存活一段时间从而能监听到消息
            Thread.sleep(100 * 1000);
            //关闭资源
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
