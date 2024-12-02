package com.example.demo.calendar;

import com.example.demo.calendar.Controller.CalendarController;
import com.example.demo.calendar.Controller.UserController;
import com.example.demo.calendar.repository.JdbcRepository;
import com.example.demo.calendar.repository.OldRepository;
import com.example.demo.calendar.service.CalendarDao;
import com.example.demo.calendar.service.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCalendarFactory {

    @Bean
    public CalendarController calendarController() {
        return new CalendarController(calendarDao(), userDao());
    }

    @Bean
    public UserController userController() {
        return new UserController(userDao());
    }

    @Bean
    public CalendarDao calendarDao() {
        return new CalendarDao(connectionmaker());
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionmaker());
    }

    @Bean
    public JdbcRepository connectionmaker() {
        return new OldRepository();
    }

}
