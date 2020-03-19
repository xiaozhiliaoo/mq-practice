package org.study.mq.rocketMQ.dt.message;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionSpringProducer {

    private Logger logger = Logger.getLogger(getClass());

    private String producerGroupName;

    private String nameServerAddress;

    private int corePoolSize = 1;

    private int maximumPoolSize = 5;

    private long keepAliveTime = 100;

    private TransactionMQProducer producer;

    private TransactionListener transactionListener;

    public TransactionSpringProducer(String producerGroupName, String nameServerAddress,
                                     int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                     TransactionListener transactionListener) {
        this.producerGroupName = producerGroupName;
        this.nameServerAddress = nameServerAddress;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.transactionListener = transactionListener;
    }

    public void init() throws Exception {
        logger.info("开始启动消息生产者服务...");

        //创建一个消息生产者，并设置一个消息生产者组
        producer = new TransactionMQProducer(producerGroupName);
        //指定 NameServer 地址
        producer.setNamesrvAddr(nameServerAddress);
        //初始化 TransactionSpringProducer，整个应用生命周期内只需要初始化一次


        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000), (Runnable r) -> {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        });
        //设置事务回查线程
        producer.setExecutorService(executorService);

        producer.setTransactionListener(transactionListener);
        producer.start();

        logger.info("消息生产者服务启动成功.");
    }

    public void destroy() {
        logger.info("开始关闭消息生产者服务...");

        producer.shutdown();

        logger.info("消息生产者服务已关闭.");
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}
