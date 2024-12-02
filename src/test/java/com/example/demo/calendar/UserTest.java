package com.example.demo.calendar;


import com.example.demo.calendar.entity.Calendar;
import com.example.demo.calendar.entity.User;
import com.example.demo.calendar.entity.UserCalendarRequest;
import com.example.demo.calendar.service.CalendarDao;
import com.example.demo.calendar.service.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;


public class UserTest {

    @Test
    public void addUser() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("한씨");
        user.setPassword("a123456");
        UserDao userDao = new UserDao();
        userDao.add(user);
    }

    @Test
    public void getUser() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("한씨");
        user.setPassword("a123456");
        UserDao userDao = new UserDao();
        User login = userDao.login(user);
        System.out.println(login.getId());
    }

    @Test
    public void addCalendar() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId(1L);
        user.setName("한씨");
        user.setPassword("a123456");
        Calendar calendar = new Calendar();
        calendar.setUserName(user.getName());
        calendar.setDetails("18시에 밥을먹는다.");
        UserCalendarRequest userCalendarRequest = new UserCalendarRequest();
        userCalendarRequest.setUser(user);
        userCalendarRequest.setCalendar(calendar);
        CalendarDao calendarDao = new CalendarDao();
        calendarDao.addCalender(userCalendarRequest);
    }

    @Test
    public void getCalendar() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setId(1L);
        CalendarDao calendarDao = new CalendarDao();
        List<Calendar> calendar = calendarDao.getCalendar(user,"2024-12-01");
        Assertions.assertNotNull(calendar);
        Assertions.assertTrue(calendar.size() > 0);
    }

    @Test
    public void pageTest() throws SQLException, ClassNotFoundException {
        CalendarDao calendarDao = new CalendarDao();
        List<Calendar> pageCalendar = calendarDao.getPageCalendar(1, 10);

        Assertions.assertNotNull(pageCalendar);
        Assertions.assertTrue(pageCalendar.size()<=10);
        System.out.println(pageCalendar.size());

    }
}
