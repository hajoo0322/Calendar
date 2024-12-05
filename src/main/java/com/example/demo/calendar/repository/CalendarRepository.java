package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import com.example.demo.calendar.exception.IdException;
import com.example.demo.calendar.repository.controller_execution.*;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CalendarRepository {
    JdbcRepository jdbcRepository;

    public CalendarRepository(JdbcRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public Calendar addCalender(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<AllRounder,AllRounder> calendarStatement;
        calendarStatement = new AddStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder).getCalendar();
    }

    public List<Calendar> getCalendar(Long id) throws ClassNotFoundException, SQLException, IdException {
        AllRounder allRounder = new AllRounder();
        allRounder.setId(id);
        CalendarStatement<AllRounder,List<Calendar>> calendarStatement = new GetStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }

    public Calendar getPortionCalendar(Long id) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<Long,Calendar> calendarStatement =new GetPortionStatement(jdbcRepository);
        return calendarStatement.calendarStatement(id);
    }

    public Calendar changeDetails(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<AllRounder, Calendar> calendarStatement = new ChangeStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }

    public void deleteCalendar(String detail) throws SQLException, ClassNotFoundException, IdException {
        CalendarStatement<String, Object> calendarStatement = new DeleteStatement(jdbcRepository);
        calendarStatement.calendarStatement(detail);
    }

    public List<Calendar> getPageCalendar(int page, int pageSize) throws ClassNotFoundException, SQLException, IdException {
        AllRounder allRounder =new AllRounder();
        allRounder.setPage(page);
        allRounder.setPageSize(pageSize);
        CalendarStatement<AllRounder, List<Calendar>> calendarStatement = new GetPageStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }
}
