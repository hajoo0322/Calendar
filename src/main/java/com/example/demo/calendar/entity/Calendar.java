package com.example.demo.calendar.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Calendar {
    Long id;
    String userName;
    String date;
    String details;

    public Calendar() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = now.format(dateTimeFormatter);
    }
}
