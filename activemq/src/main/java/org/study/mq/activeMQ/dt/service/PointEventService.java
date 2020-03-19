package org.study.mq.activeMQ.dt.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.study.mq.activeMQ.dt.constant.EventProcess;
import org.study.mq.activeMQ.dt.constant.EventType;
import org.study.mq.activeMQ.dt.dao.PointEventDao;
import org.study.mq.activeMQ.dt.exception.BusinessException;
import org.study.mq.activeMQ.dt.model.Event;
import org.study.mq.activeMQ.dt.model.Point;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PointEventService {

    @Resource
    private PointEventDao pointEventDao;

    @Resource
    private PointService pointService;

    public int newEvent(Event event) {
        if (event != null) {
            return pointEventDao.insert(event);
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

    public List<Event> getPublishedEventList() {
        return pointEventDao.getByProcess(EventProcess.PUBLISHED.getValue());
    }

    public void executeEvent(Event event) {
        if (event != null) {
            String eventProcess = event.getProcess();
            if ((EventProcess.PUBLISHED.getValue().equals(eventProcess))
                    && (EventType.NEW_POINT.getValue().equals(event.getType()))) {
                Point point = JSON.parseObject(event.getContent(), Point.class);
                pointService.newPoint(point);

                event.setProcess(EventProcess.PROCESSED.getValue());
                pointEventDao.updateProcess(event);
            }
        }
    }
}
