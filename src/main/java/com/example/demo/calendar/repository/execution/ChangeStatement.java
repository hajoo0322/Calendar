package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChangeStatement implements CalendarStatement<AllRounder, Calendar>{
    JdbcRepository jdbcRepository;

    public ChangeStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public Calendar calendarStatement(AllRounder allRounder) throws SQLException, ClassNotFoundException {
        try (Connection c = jdbcRepository.makeConnection();

             PreparedStatement ps = c.prepareStatement(
                     "UPDATE calendar SET details = ? WHERE details =?"
             )) {
            ps.setString(1, allRounder.getDetails());
            ps.setString(2, allRounder.getCalendar().getDetails());
            ps.executeUpdate();

            try (PreparedStatement ps2 = c.prepareStatement(
                    "insert into calendar(date_modified) values (?)"
            )) {
                LocalDate now = LocalDate.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String format = now.format(dateTimeFormatter);
                ps2.setString(1, format);
                ps2.executeUpdate();

                allRounder.getCalendar().setDetails(allRounder.getDetails());

                return allRounder.getCalendar();
            }
        } catch (SQLException e) {
            throw new SQLException("데이터 베이스 연결 실패" + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("데이터베이스 드라이버 혹은 쿼리에 문제발생");
        }
    }
}
