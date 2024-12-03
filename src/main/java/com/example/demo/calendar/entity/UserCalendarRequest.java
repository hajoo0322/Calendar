package com.example.demo.calendar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCalendarRequest {
    private User user;
    private Calendar calendar;
}
