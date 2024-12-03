package com.example.demo.calendar.repository.controller_execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddStatement implements CalendarStatement<AllRounder,AllRounder> {
    JdbcRepository jdbcRepository;

    public AddStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public AllRounder calendarStatement(AllRounder allRounder) throws SQLException, ClassNotFoundException {
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "insert into calendar(id, userName, date, details) values (?,?,?,?)"
             )) {


            ps.setLong(1, allRounder.getUser().getId());
            ps.setString(2, allRounder.getCalendar().getUserName());
            ps.setString(3, allRounder.getCalendar().getDate());
            ps.setString(4, allRounder.getCalendar().getDetails());
            return allRounder;
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        }catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생" + e.getMessage());
        }
    }
}
