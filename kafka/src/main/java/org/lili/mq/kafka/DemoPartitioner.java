package org.lili.mq.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lili
 * @date 2021/7/4 16:42
 */
public class DemoPartitioner implements Partitioner {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if (null == keyBytes || keyBytes.length < 1) {
            return atomicInteger.getAndIncrement() % numPartitions;
        }
        //借用String的hashCode的计算方式
        int hash = 0;
        for (byte b : keyBytes) {
            hash = 31 * hash + b;
        }
        return hash % numPartitions;
    }

    @Override
    public void close() {
    }
}
