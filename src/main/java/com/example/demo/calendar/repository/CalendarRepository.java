package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import com.example.demo.calendar.repository.execution.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class CalendarRepository {
    JdbcRepository jdbcRepository;

    public Calendar addCalender(AllRounder allRounder) throws ClassNotFoundException, SQLException {
        CalendarStatement<AllRounder,AllRounder> calendarStatement;
        calendarStatement = new AddStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder).getCalendar();
    }

    public List<Calendar> getCalendar(Long id, String date) throws ClassNotFoundException, SQLException {
        AllRounder allRounder = new AllRounder();
        allRounder.setId(id);
        allRounder.setDate(date);
        CalendarStatement<AllRounder,List<Calendar>> calendarStatement = new GetStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }

    public Calendar getPortionCalendar(User user) throws ClassNotFoundException, SQLException {
        CalendarStatement<User,Calendar> calendarStatement =new GetPortionStatement(jdbcRepository);
        try {
            return calendarStatement.calendarStatement(user);
        } catch (RuntimeException e) {
            log.error("오류 발생"+user.getId()+e.getMessage());
            return null;
        }
    }

    public Calendar changeDetails(AllRounder allRounder) throws ClassNotFoundException, SQLException {
        CalendarStatement<AllRounder, Calendar> calendarStatement = new ChangeStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);

    }

    public void deleteCalendar(String detail) throws SQLException, ClassNotFoundException {
        CalendarStatement<String, Object> calendarStatement = new DeleteStatement(jdbcRepository);
        calendarStatement.calendarStatement(detail);
    }

    public List<Calendar> getPageCalendar(int page, int pageSize) throws ClassNotFoundException, SQLException {
        AllRounder allRounder =new AllRounder();
        allRounder.setPage(page);
        allRounder.setPageSize(pageSize);
        CalendarStatement<AllRounder, List<Calendar>> calendarStatement = new GetPageStatement(jdbcRepository);
        return calendarStatement.calendarStatement(allRounder);
    }
}
