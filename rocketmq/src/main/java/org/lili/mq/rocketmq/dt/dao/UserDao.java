package org.lili.mq.rocketmq.dt.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.lili.mq.rocketmq.dt.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Repository
public class UserDao extends JdbcDaoSupport {

    public String getId(){
        String id = UUID.randomUUID().toString().replace("-", "");

        return id;
    }

    public String insert(String id, String userName) {

        getJdbcTemplate().update("insert into t_user(id, user_name) values(?, ?) ",
                (PreparedStatement ps) -> {
                    ps.setString(1, id);
                    ps.setString(2, userName);
                }

        );
        return id;
    }

    public User getById(String userId) {
        return getJdbcTemplate().queryForObject("select id, user_name from t_user where id = '" + userId + "'", (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            return user;
        });
    }

}
