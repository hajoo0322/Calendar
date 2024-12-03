package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStatement implements CalendarStatement<String , Object>{
   JdbcRepository jdbcRepository;

    public DeleteStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public Object calendarStatement(String detail) throws SQLException, ClassNotFoundException {
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "DELETE FROM calendar WHERE details = ?"
             )) {
            ps.setString(1, detail);
            ps.executeUpdate();

            return null;
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생");
        }
    }
}
