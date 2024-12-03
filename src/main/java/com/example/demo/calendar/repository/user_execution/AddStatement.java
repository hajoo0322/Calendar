package com.example.demo.calendar.repository.user_execution;

import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements UserStatement<User>{
JdbcRepository jdbcRepository;

    public AddStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public User userStatement(User user) throws SQLException, ClassNotFoundException {
        try (Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into users( name, password) values (?,?)"
        )) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            ps.close();
            c.close();
        }
    }
}
