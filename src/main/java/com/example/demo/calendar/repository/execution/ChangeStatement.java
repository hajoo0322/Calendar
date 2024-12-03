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
        Connection c = jdbcRepository.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "UPDATE calendar SET details = ? WHERE details =?"
        );
        ps.setString(1,allRounder.getDetails());
        ps.setString(2,allRounder.getCalendar().getDetails());
        ps.executeUpdate();

        PreparedStatement ps2 = c.prepareStatement(
                "insert into calendar(date_modified) values (?)"
        );
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = now.format(dateTimeFormatter);
        ps2.setString(1,format);
        ps2.executeUpdate();

        allRounder.getCalendar().setDetails(allRounder.getDetails());

        ps2.close();
        ps.close();
        c.close();

        return allRounder.getCalendar();
    }
}
