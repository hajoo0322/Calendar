package com.example.demo.calendar.repository.user_execution;

import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginStatement implements UserStatement<User>{
    JdbcRepository jdbcRepository;

    public LoginStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public User userStatement(User user) throws SQLException, ClassNotFoundException, IdException {
        try(Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE name = ?"
        )) {
            String name = user.getName();
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("password").equals(user.getPassword())) {
                    user.setId(rs.getLong("id"));
                    return user;
                } else {
                    throw new IdException("비밀번호가 잘못됨");
                }
            } else {
                throw new IdException("기존의 저장된 유저의 정보가 없음");
            }
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생" + e.getMessage());
        }
    }
}
