package com.example.demo.calendar.repository.user_execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NameChangeStatement implements UserStatement<AllRounder>{
   JdbcRepository jdbcRepository;

    public NameChangeStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public User userStatement(AllRounder allRounder) throws SQLException, ClassNotFoundException, IdException {
        try(Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "UPDATE users SET name = ? WHERE name = ?"
        )) {
            ps.setString(1, allRounder.getUserNameChange().getName());
            ps.setString(2, allRounder.getUser().getName());
            ps.executeUpdate();
            allRounder.getUser().setName(allRounder.getUserNameChange().getName());
            return allRounder.getUser();
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생" + e.getMessage());
        }
    }
}
