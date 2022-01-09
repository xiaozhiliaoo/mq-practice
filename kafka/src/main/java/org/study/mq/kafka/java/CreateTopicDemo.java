package org.study.mq.kafka.java;

import kafka.admin.TopicCommand;

/**
 * @author lili
 * @date 2021/7/4 16:49
 */
public class CreateTopicDemo {
    public static void main(String[] args) {
        //demo: 创建一个副本数为2，分区数为4的主题：topic-test8
        String[] options = new String[]{
                "--create",
                "--zookeeper", "192.168.0.2:2181/kafka100",
                "--replication-factor", "2",
                "--partitions", "4",
                "--topic", "topic-test8"
        };
        TopicCommand.main(options);
    }
}
