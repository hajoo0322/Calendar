package com.example.demo.calendar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllRounder {
    Long id;
    String date;
    String details;
    Calendar calendar;
    User user;
    User userNameChange;
}
