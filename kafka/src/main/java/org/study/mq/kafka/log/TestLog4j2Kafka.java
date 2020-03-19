package org.study.mq.kafka.log;

import org.apache.log4j.Logger;

public class TestLog4j2Kafka {

    private static Logger logger = Logger.getLogger(TestLog4j2Kafka.class);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 5; i++) {

            logger.info("这是从 TestLog4j2Kafka 产生的消息 【" + i + "】 ..  ");

            Thread.sleep(1000);
        }
    }

}
