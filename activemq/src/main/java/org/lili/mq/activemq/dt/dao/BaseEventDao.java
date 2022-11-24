package org.lili.mq.activemq.dt.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.CollectionUtils;
import org.lili.mq.activemq.dt.model.Event;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseEventDao extends JdbcDaoSupport {

    public Integer insert(Event event) {

        return getJdbcTemplate().update("insert into t_event(type, process, content, create_time, update_time) values(?, ?, ?, now(), now()) ",
                (PreparedStatement ps) -> {
                    ps.setString(1, event.getType());
                    ps.setString(2, event.getProcess());
                    ps.setString(3, event.getContent());

                });
    }

    public Integer updateProcess(Event event) {
        return getJdbcTemplate().update("update t_event set process = ?, update_time = now() where id = ? ",
                (PreparedStatement ps) -> {
                    ps.setString(1, event.getProcess());
                    ps.setInt(2, event.getId());
                }
        );
    }

    public List<Event> getByProcess(String process) {
        List<Event> result = new ArrayList<>();

        List<Map<String, Object>> list = getJdbcTemplate().queryForList("select id, type, process, content from t_event where process ="
                + " '" + process + "' ");
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(map -> {
                Event event = new Event();
                event.setId((Integer) map.get("id"));
                event.setType((String) map.get("type"));
                event.setProcess((String) map.get("process"));
                event.setContent((String) map.get("content"));
                result.add(event);
            });
        }

        return result;
    }
}
