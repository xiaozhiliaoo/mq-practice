package org.lili.mq.activemq.dt.service;

import org.lili.mq.activemq.dt.constant.EventProcess;
import org.lili.mq.activemq.dt.constant.EventType;
import org.lili.mq.activemq.dt.exception.BusinessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.lili.mq.activemq.dt.dao.UserEventDao;
import org.lili.mq.activemq.dt.model.Event;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

@Service
public class UserEventService {

    @Resource
    private UserEventDao userEventDao;

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    @Resource(name = "topicDistributedTransaction")
    private Destination topic;

    public int newEvent(Event event) {
        if (event != null) {
            return userEventDao.insert(event);
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

    public List<Event> getNewEventList() {
        return userEventDao.getByProcess(EventProcess.NEW.getValue());
    }

    public void executeEvent(Event event) {
        if (event != null) {
            String eventProcess = event.getProcess();
            if ((EventProcess.NEW.getValue().equals(eventProcess))
                    && (EventType.NEW_USER.getValue().equals(event.getType()))) {
                String messageContent = event.getContent();
                jmsTemplate.send(topic, (Session session) -> {
                    TextMessage msg = session.createTextMessage();
                    // 设置消息内容
                    msg.setText(messageContent);
                    return msg;
                });

                event.setProcess(EventProcess.PUBLISHED.getValue());
                userEventDao.updateProcess(event);
            }
        }
    }

}
