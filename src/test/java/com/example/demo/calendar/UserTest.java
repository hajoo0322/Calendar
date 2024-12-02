package com.example.demo.calendar;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        CalendarDao calendarDao = new CalendarDao();
        calendarDao.addCalender(calendar,user);
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
}
