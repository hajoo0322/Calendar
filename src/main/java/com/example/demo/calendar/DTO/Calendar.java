package com.example.demo.calendar.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "할일이 누락됨",groups = {ValidationGroup.Calendar.class})
    @Size(max = 200,message = "할일은 최대 200자까지입력가능")
    String details;

    public Calendar() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = now.format(dateTimeFormatter);
    }
}
