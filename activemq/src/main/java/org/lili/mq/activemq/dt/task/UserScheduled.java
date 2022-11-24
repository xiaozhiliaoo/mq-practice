package org.lili.mq.activemq.dt.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.lili.mq.activemq.dt.model.Event;
import org.lili.mq.activemq.dt.service.UserEventService;

import java.util.List;

@Component
public class UserScheduled {

    @Autowired
    private UserEventService userEventService;

    @Scheduled(cron = "*/5 * * * * *")
    public void executeEvent() {
        List<Event> eventList = userEventService.getNewEventList();
        if (!CollectionUtils.isEmpty(eventList)) {
            System.out.println("新建用户的事件记录总数：" + eventList.size());

            for (Event event : eventList) {
                userEventService.executeEvent(event);
            }
        } else {
            System.out.println("待处理的事件总数：0");
        }

    }
}
