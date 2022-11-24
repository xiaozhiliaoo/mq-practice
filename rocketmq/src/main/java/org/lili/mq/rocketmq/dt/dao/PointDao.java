package org.lili.mq.rocketmq.dt.dao;

import org.lili.mq.rocketmq.dt.model.Point;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PointDao extends JdbcDaoSupport {

    public String insert(Point point) {
        String id = UUID.randomUUID().toString().replace("-", "");

        getJdbcTemplate().update("insert into t_point(id, user_id, amount) values(?, ?, ?) ",
                (PreparedStatement ps) -> {
                    ps.setString(1, id);
                    ps.setString(2, point.getUserId());
                    ps.setInt(3, point.getAmount());
                }

        );
        return id;
    }

    public Point getByUserId(String userId) {
        List<Map<String, Object>> list = getJdbcTemplate().queryForList("select id, user_id, amount from t_point where user_id = '" + userId + "'");
        if ((list == null) || (list.size() == 0)) {
            return null;
        } else {
            Point point = new Point();
            point.setId((String) list.get(0).get("id"));
            point.setUserId((String) list.get(0).get("user_id"));
            point.setAmount((Integer) list.get(0).get("amount"));
            return point;
        }
    }

}
