package org.study.mq.activeMQ.dt.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.mq.activeMQ.dt.constant.EventProcess;
import org.study.mq.activeMQ.dt.constant.EventType;
import org.study.mq.activeMQ.dt.dao.UserDao;
import org.study.mq.activeMQ.dt.model.Point;
import org.study.mq.activeMQ.dt.model.Event;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserEventService userEventService;

    @Transactional
    public void newUser(String userName, Integer pointAmount) {
        // 1.保存用户
        String userId = userDao.insert(userName);

        // 2.新增事件
        Event event = new Event();
        event.setType(EventType.NEW_USER.getValue());
        event.setProcess(EventProcess.NEW.getValue());
        Point point = new Point();
        point.setUserId(userId);
        point.setAmount(pointAmount);
        // 将对象转成 json 字符串存到事件的内容字段中
        event.setContent(JSON.toJSONString(point));
        userEventService.newEvent(event);
    }

}
