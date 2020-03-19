package org.study.mq.kafka.java;

import org.apache.kafka.clients.producer.*;

import java.util.HashMap;
import java.util.Map;

public class ProducerSample {

    public static void main(String[] args) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String topic = "test-topic";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 1"));
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 2"));
        producer.send(new ProducerRecord(topic, "idea-key2", "java-message 3"));

        producer.close();
    }

}
