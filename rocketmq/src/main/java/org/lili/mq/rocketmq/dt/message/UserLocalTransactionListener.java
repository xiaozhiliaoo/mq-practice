package org.lili.mq.rocketmq.dt.message;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.lili.mq.rocketmq.dt.model.UserPointMessage;
import org.lili.mq.rocketmq.dt.service.UserService;

import javax.annotation.Resource;

public class UserLocalTransactionListener implements TransactionListener {

    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        logger.info("本地事务执行...");
        logger.info("消息标签是 " + new String(message.getTags()));
        logger.info("消息内容是 " + new String(message.getBody()));

        //从消息体中获取积分消息对象
        UserPointMessage userPointMessage = JSON.parseObject(message.getBody(), UserPointMessage.class);
        //保存用户记录并提交本地事务
        userService.saveUser(userPointMessage.getUserId(), userPointMessage.getUserName());

        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        logger.info("消息服务器调用消息回查接口");
        logger.info("消息标签是：" + new String(messageExt.getTags()));
        logger.info("消息内容是：" + new String(messageExt.getBody()));

        //从消息体中获取积分消息对象
        UserPointMessage pointMessage = JSON.parseObject(messageExt.getBody(), UserPointMessage.class);
        if (pointMessage != null) {
            String userId = pointMessage.getUserId();
            if (userService.getById(userId) != null) {
                logger.info("本地插入用户表成功！");
                //表示本地事务执行成功
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
