package com.example.demo.calendar.DTO;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllRounder {
    Long id;
    String date;
    String details;
    @Valid
    Calendar calendar;
    @Valid
    User user;
    User userNameChange;
    int page;
    int pageSize;
}
