package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.UserCalendarRequest;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AddStatement implements CalendarStatement<UserCalendarRequest,UserCalendarRequest> {
    JdbcRepository jdbcRepository;

    public AddStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public UserCalendarRequest calendarStatement(UserCalendarRequest calendar) throws SQLException, ClassNotFoundException {
        Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into calendar(id, userName, date, details) values (?,?,?,?)"
        );

        ps.setLong(1, calendar.getUser().getId());
        ps.setString(2, calendar.getCalendar().getUserName());
        ps.setString(3, calendar.getCalendar().getDate());
        ps.setString(4, calendar.getCalendar().getDetails());
        ps.executeUpdate();
        ps.close();
        c.close();

        return calendar;
    }
}
