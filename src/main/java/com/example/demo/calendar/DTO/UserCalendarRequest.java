package com.example.demo.calendar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCalendarRequest {
    private User user;
    private Calendar calendar;
}
