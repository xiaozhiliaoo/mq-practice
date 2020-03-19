package org.study.mq.rocketMQ.order;

import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class OrderMessageListener implements MessageListenerOrderly {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        //默认 list 里只有一条消息，可以通过设置参数来批量接收消息
        if (list != null) {
            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                logger.info(LocalDateTime.now().format(timeFormatter) + " 接收到消息：");

                //模拟业务处理消息的时间
                Thread.sleep(new Random().nextInt(1000));

                for (MessageExt ext : list) {
                    try {
                        logger.info(LocalDateTime.now().format(timeFormatter) + " 消息内容：" + new String(ext.getBody(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
