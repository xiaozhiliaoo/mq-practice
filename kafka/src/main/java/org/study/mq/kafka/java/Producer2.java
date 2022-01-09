package org.study.mq.kafka.java;

import org.apache.kafka.clients.producer.*;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.study.mq.kafka.java.Const.TOPIC_2;

public class Producer2 {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.56.101:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("group.id", "testGroup1");

        Producer<String, String> producer = new KafkaProducer<>(props);

        while (true) {
            String message = "kafka_message-" + new Date().getTime() + "-edited by hidden.zhu";
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_2, message);
            try {
                Future<RecordMetadata> future = producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        System.out.print(metadata.offset() + "    ");
                        System.out.print(metadata.topic() + "    ");
                        System.out.println(metadata.partition());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
