package org.lili.mq.rocketmq.order;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class OrderProducer {

    private Logger logger = Logger.getLogger(getClass());

    private String producerGroupName;

    private String nameServerAddr;

    private DefaultMQProducer producer;

    public OrderProducer(String producerGroupName, String nameServerAddr) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddr = nameServerAddr;
    }

    public void init() throws Exception {
        logger.info("开始启动生产者服务...");

        //创建一个消息生产者，并设置一个消息生产者组
        producer = new DefaultMQProducer(producerGroupName);
        //指定 NameServer 地址
        producer.setNamesrvAddr(nameServerAddr);
        //初始化 Producer，整个应用生命周期内只需要初始化一次
        producer.start();

        logger.info("生产者服务启动成功.");
    }

    public void destroy() {
        logger.info("开始关闭生产者服务...");

        producer.shutdown();

        logger.info("生产者服务已关闭.");
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}
