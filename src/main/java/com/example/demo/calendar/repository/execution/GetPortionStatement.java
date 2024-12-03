package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetPortionStatement implements CalendarStatement<User, Calendar>{
    JdbcRepository jdbcRepository;

    public GetPortionStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public Calendar calendarStatement(User user) throws SQLException, ClassNotFoundException {
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT  * FROM calendar WHERE id = ? LIMIT 1"
             )) {                    //1개만 조회하도록 변경
            ps.setLong(1, user.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Calendar calendar = new Calendar();
                    calendar.setUserName(rs.getString("userName"));
                    calendar.setDate(rs.getString("date"));
                    calendar.setDetails(rs.getString("details"));
                    return calendar;
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                throw new SQLException("데이터베이스 연결실패");
            }
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("데이터베이스 드라이버를 찾을수 없습니다.");
        }
    }
}
