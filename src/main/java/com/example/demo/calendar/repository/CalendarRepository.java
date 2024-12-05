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
    UserCalendarFactory userCalendarFactory;

    public CalendarRepository(UserCalendarFactory userCalendarFactory) {
        this.userCalendarFactory = userCalendarFactory;
    }

    public Calendar addCalender(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<AllRounder,AllRounder> calendarStatement;
        calendarStatement = userCalendarFactory.addStatement();
        return calendarStatement.calendarStatement(allRounder).getCalendar();
    }

    public List<Calendar> getCalendar(Long id) throws ClassNotFoundException, SQLException, IdException {
        AllRounder allRounder = new AllRounder();
        allRounder.setId(id);
        CalendarStatement<AllRounder,List<Calendar>> calendarStatement = userCalendarFactory.getStatement();
        return calendarStatement.calendarStatement(allRounder);
    }

    public Calendar getPortionCalendar(Long id) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<Long,Calendar> calendarStatement = userCalendarFactory.getPortionStatement();
        return calendarStatement.calendarStatement(id);
    }

    public Calendar changeDetails(AllRounder allRounder) throws ClassNotFoundException, SQLException, IdException {
        CalendarStatement<AllRounder, Calendar> calendarStatement = userCalendarFactory.changeStatement();
        return calendarStatement.calendarStatement(allRounder);
    }

    public void deleteCalendar(String detail) throws SQLException, ClassNotFoundException, IdException {
        CalendarStatement<String, Object> calendarStatement = userCalendarFactory.deleteStatement();
        calendarStatement.calendarStatement(detail);
    }

    public List<Calendar> getPageCalendar(int page, int pageSize) throws ClassNotFoundException, SQLException, IdException {
        AllRounder allRounder =new AllRounder();
        allRounder.setPage(page);
        allRounder.setPageSize(pageSize);
        CalendarStatement<AllRounder, List<Calendar>> calendarStatement = userCalendarFactory.getPageStatement();
        return calendarStatement.calendarStatement(allRounder);
    }
}
