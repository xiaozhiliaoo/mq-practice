package org.study.mq.kafka.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class SpringProducer {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-kafka.xml");

        KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate) ctx.getBean("kafkaTemplate");
        kafkaTemplate.send("kafka-topic", "我的测试消息1");
        kafkaTemplate.send("kafka-topic", "我的测试消息2");
        kafkaTemplate.send("kafka-topic", "我的测试消息3");
    }

}
