package org.study.mq.rocketMQ.order;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.study.mq.rocketMQ.spring.SpringConsumer;

public class OrderConsumerTest {

    private ApplicationContext container;

    @Before
    public void setup() {
        container = new ClassPathXmlApplicationContext("classpath:spring-consumer-order.xml");
    }

    @Test
    public void consume() throws Exception {
        OrderConsumer consumer = container.getBean(OrderConsumer.class);

        Thread.sleep(200 * 1000);

        consumer.destroy();
    }
}
