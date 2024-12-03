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
            return user;
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생" + e.getMessage());
        }
    }
}
