package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPageStatement implements CalendarStatement<AllRounder, List<Calendar>> {
    JdbcRepository jdbcRepository;

    public GetPageStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public List<Calendar> calendarStatement(AllRounder allRounder) throws SQLException, ClassNotFoundException {
        int wherePage = (allRounder.getPage() - 1) * allRounder.getPageSize();
        try (Connection c = jdbcRepository.makeConnection();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT * FROM calendar LIMIT ? OFFSET ?"
             )) {

            ps.setInt(1, allRounder.getPageSize());
            ps.setInt(2, wherePage);
            try (ResultSet rs = ps.executeQuery()) {
                List<Calendar> calendarList = new ArrayList<>();
                while (rs.next()) {
                    Calendar calendar = new Calendar();
                    calendar.setUserName(rs.getString("username"));
                    calendar.setDetails(rs.getString("details"));
                    calendar.setDate(rs.getString("date"));
                    calendarList.add(calendar);
                }
                return calendarList;
            }
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 문제발생");
        }
    }
}
