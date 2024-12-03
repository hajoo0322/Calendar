package com.example.demo.calendar.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private User user;
    private User changeUserName;
}
