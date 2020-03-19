package org.study.mq.activeMQ.dt.service;

import org.springframework.stereotype.Service;
import org.study.mq.activeMQ.dt.dao.PointDao;
import org.study.mq.activeMQ.dt.exception.BusinessException;
import org.study.mq.activeMQ.dt.model.Point;

import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    public String newPoint(Point point) {
        if (point != null) {
            return dao.insert(point);
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

}
