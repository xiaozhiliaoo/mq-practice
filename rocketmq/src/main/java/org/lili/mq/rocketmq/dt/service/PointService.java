package org.lili.mq.rocketmq.dt.service;

import org.lili.mq.rocketmq.dt.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.lili.mq.rocketmq.dt.dao.PointDao;
import org.lili.mq.rocketmq.dt.model.Point;

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
