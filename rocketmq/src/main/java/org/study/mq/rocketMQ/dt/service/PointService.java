package org.study.mq.rocketMQ.dt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.study.mq.rocketMQ.dt.dao.PointDao;
import org.study.mq.rocketMQ.dt.exception.BusinessException;
import org.study.mq.rocketMQ.dt.model.Point;

import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    @Transactional(rollbackFor = Exception.class)
    public String savePoint(Point point) {
        if ((point != null) && (point.getUserId() != null)) {
            Point queryPoint = dao.getByUserId(point.getUserId());
            if (queryPoint != null) {
                return queryPoint.getId();
            } else {
                return dao.insert(point);
            }
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

}
