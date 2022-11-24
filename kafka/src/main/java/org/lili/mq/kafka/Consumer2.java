package org.lili.mq.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author lili
 * @date 2021/7/4 16:34
 */
public class Consumer2 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.56.101:9092");
        properties.put("session.timeout.ms", 10000);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.hidden.client.DemoDeserializer");
        properties.put("client.id", "hidden-consumer-client-id-zzh-2");
        KafkaConsumer<String, Company> consumer = new KafkaConsumer<String, Company>(properties);
        consumer.subscribe(Arrays.asList(Const.TOPIC_2));
        try {
            while (true) {
                ConsumerRecords<String, Company> records = consumer.poll(100);
                for (ConsumerRecord<String, Company> record : records) {
                    String info = String.format("topic=%s, partition=%s, offset=%d, consumer=%s, country=%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                    System.out.println(info);
                }
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception != null) {
                            String error = String.format("Commit failed for offsets {}", offsets, exception);
                            System.out.println(error);
                        }
                    }
                });
            }
        } finally {
            consumer.close();
        }
    }
}
