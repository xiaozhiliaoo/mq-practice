package org.lili.mq.activemq.dt.service;

import org.lili.mq.activemq.dt.dao.PointDao;
import org.lili.mq.activemq.dt.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.lili.mq.activemq.dt.model.Point;

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
