package com.example.demo.calendar.repository.execution;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AddStatement implements CalendarStatement<AllRounder,AllRounder> {
    JdbcRepository jdbcRepository;

    public AddStatement(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    @Override
    public AllRounder calendarStatement(AllRounder allRounder) throws SQLException, ClassNotFoundException {
        Connection c = jdbcRepository.makeConnection();
        PreparedStatement ps = c.prepareStatement(
                "insert into calendar(id, userName, date, details) values (?,?,?,?)"
        );

        ps.setLong(1, allRounder.getUser().getId());
        ps.setString(2, allRounder.getCalendar().getUserName());
        ps.setString(3, allRounder.getCalendar().getDate());
        ps.setString(4, allRounder.getCalendar().getDetails());
        ps.executeUpdate();
        ps.close();
        c.close();

        return allRounder;
    }
}
