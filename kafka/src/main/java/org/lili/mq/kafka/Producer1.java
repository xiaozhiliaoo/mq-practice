package org.lili.mq.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class Producer1 {

    public static void main(String[] args) {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", Config.IP);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("group.id", "testGroup1");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            producer.send(new ProducerRecord(Const.TOPIC, "idea-key2", "java-message 1"));
            producer.send(new ProducerRecord(Const.TOPIC, "idea-key2", "java-message 2"));
            producer.send(new ProducerRecord(Const.TOPIC, "idea-key2", "java-message 3"));
        }
        System.out.println("send done");
        producer.close();
    }

}
