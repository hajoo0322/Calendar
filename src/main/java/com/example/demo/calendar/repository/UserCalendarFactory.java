package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.repository.controller_execution.*;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCalendarFactory {
    JdbcRepository jdbcRepository;

    public UserCalendarFactory(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public CalendarStatement<AllRounder,AllRounder> addStatement() {
        return new AddStatement(jdbcRepository);
    }

    public CalendarStatement<AllRounder, Calendar> changeStatement() {
        return new ChangeStatement(jdbcRepository);
    }

    public CalendarStatement<AllRounder, List<Calendar>> getStatement() {
        return new GetStatement(jdbcRepository);
    }

    public CalendarStatement<Long ,Calendar > getPortionStatement() {
        return new GetPortionStatement(jdbcRepository);
    }

    public CalendarStatement<String, Object> deleteStatement() {
        return new DeleteStatement(jdbcRepository);
    }

    public CalendarStatement<AllRounder, List<Calendar>> getPageStatement() {
        return new GetPageStatement(jdbcRepository);
    }
}
