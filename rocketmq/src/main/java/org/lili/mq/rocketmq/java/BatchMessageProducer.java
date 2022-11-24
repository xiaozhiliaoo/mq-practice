package org.lili.mq.rocketmq.java;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by niwei on 2018/5/29.
 */
public class BatchMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("niwei_producer_group");
        producer.start();

        String topic = "topic_example_batch";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "100001", "Hello world a".getBytes()));
        messages.add(new Message(topic, "TagA", "100002", "Hello world b".getBytes()));
        messages.add(new Message(topic, "TagA", "100003", "Hello world c".getBytes()));
        try {
            ListSplitter splitter = new ListSplitter(messages);
            while (splitter.hasNext()) {
                try {
                    List<Message>  listItem = splitter.next();
                    producer.send(listItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        producer.shutdown();
    }
}

class ListSplitter implements Iterator<List<Message>> {
    private final int SIZE_LIMIT = 1000 * 1000;
    private final List<Message> messages;
    private int currIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize = tmpSize + 20; //for log overhead
            if (tmpSize > SIZE_LIMIT) {
                //it is unexpected that single message exceeds the SIZE_LIMIT
                //here just let it go, otherwise it will block the splitting process
                if (nextIndex - currIndex == 0) {
                    //if the next sublist has no element, add this one and then break, otherwise just break
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }

        }
        List<Message> subList = messages.subList(currIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }
}
