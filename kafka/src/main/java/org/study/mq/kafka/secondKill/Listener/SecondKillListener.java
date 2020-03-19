package org.study.mq.kafka.secondKill.Listener;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.study.mq.kafka.secondKill.config.Constants;

public class SecondKillListener {

    private static Logger logger = LoggerFactory.getLogger(SecondKillListener.class);

    @KafkaListener(id = Constants.SECOND_KILL_CONTAINER, topics = {Constants.TOPIC_SECOND_KILL})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("监听到消息记录 ===============");
        logger.info("topic = " + record.topic());
        logger.info("key = " + record.key());
        logger.info("value = " + record.value());
        logger.info("----------------------------");

        if (record.value() != null) {
            JSONObject jsonObject = JSONObject.parseObject(record.value().toString());
            logger.info("goodsId : " + jsonObject.get("goodsId"));
            logger.info("goodsStock : " + jsonObject.get("goodsStock"));

            //获取到业务数据进行相应的业务处理，比如秒杀后续的生成订单、短信通知等
        }
    }

}
