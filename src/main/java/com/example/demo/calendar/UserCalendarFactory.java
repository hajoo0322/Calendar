package com.example.demo.calendar;

import com.example.demo.calendar.repository.JdbcRepository;
import com.example.demo.calendar.repository.OldRepository;
import com.example.demo.calendar.repository.CalendarRepository;
import com.example.demo.calendar.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCalendarFactory {

    @Bean
    public CalendarRepository calendarDao() {
        return new CalendarRepository(connectionmaker());
    }

    @Bean
    public UserRepository userDao() {
        return new UserRepository(connectionmaker());
    }

    @Bean
    public JdbcRepository connectionmaker() {
        return new OldRepository();
    }

}
