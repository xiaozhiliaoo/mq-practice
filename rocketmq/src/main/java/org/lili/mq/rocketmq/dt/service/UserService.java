package org.lili.mq.rocketmq.dt.service;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.lili.mq.rocketmq.dt.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.lili.mq.rocketmq.dt.message.TransactionSpringProducer;
import org.lili.mq.rocketmq.dt.model.User;
import org.lili.mq.rocketmq.dt.model.UserPointMessage;

import javax.annotation.Resource;

@Service
public class UserService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private TransactionSpringProducer producer;

    @Resource
    private UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    public void newUserAndPoint(String userName, Integer amount) throws Exception {
        //获取用户ID
        String userId = userDao.getId();

        //发送新增积分消息
        UserPointMessage message = new UserPointMessage();
        message.setUserId(userId);
        message.setUserName(userName);
        message.setAmount(amount);

        this.sendMessage(message);
    }

    /**
     * 根据 ID 查询记录
     * @param userId
     * @return
     */
    public User getById(String userId) {
        return userDao.getById(userId);
    }

    /**
     * 保存用户记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(String userId, String userName) {
        userDao.insert(userId, userName);
    }

    /**
     * 给消费者发送消息
     */
    private void sendMessage(UserPointMessage pointMessage) throws Exception {
        //构造消息数据
        Message message = new Message();
        //主题
        message.setTopic("distributed_transaction_spring_topic");
        //子标签
        message.setTags("newUserAndPoint");

        message.setKeys(pointMessage.getUserId());

        // 将积分对象转成 json 字符串存到事件的内容字段中
        message.setBody(JSON.toJSONString(pointMessage).getBytes());

        //发送消息，并封装本地事务处理逻辑
        SendResult sendResult = producer.getProducer().sendMessageInTransaction(message, "");

        logger.info("消息发送结果：" + sendResult);
    }

}
