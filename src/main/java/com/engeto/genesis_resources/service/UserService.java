package com.engeto.genesis_resources.service;

import com.engeto.genesis_resources.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAllUsers() {
        return jdbcTemplate.query("Select * from users;", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPersonId(rs.getString("person_id"));
                user.setUuid(rs.getString("uuid"));
                return user;
            }
        });
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, surname, person_id, uuid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getPersonId(),
                user.getUuid()
        );
    }
}
