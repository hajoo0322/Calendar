package com.example.demo.calendar.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private User user;
    private User changeUserName;
}
