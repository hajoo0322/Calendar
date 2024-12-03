package com.example.demo.calendar.repository;

import com.example.demo.calendar.DTO.AllRounder;
import com.example.demo.calendar.DTO.Calendar;
import com.example.demo.calendar.DTO.User;
import com.example.demo.calendar.repository.dbconnecter.JdbcRepository;
import com.example.demo.calendar.repository.execution.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CalendarRepository {

    JdbcRepository jdbcRepository;

    public void addCalender(AllRounder allRounder) throws ClassNotFoundException, SQLException {
        CalendarStatement<AllRounder,AllRounder> calendarStatement;
        calendarStatement = new AddStatement(jdbcRepository);


        calendarStatement.calendarStatement(allRounder);
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
        return calendarStatement.calendarStatement(user);
    }

    public Calendar changeDetails(Calendar calendar,String detail) throws ClassNotFoundException, SQLException {
        AllRounder allRounder = new AllRounder();
        allRounder.setCalendar(calendar);
        allRounder.setDetails(detail);
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
