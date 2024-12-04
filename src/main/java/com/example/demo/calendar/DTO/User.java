package com.example.demo.calendar.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;

    @NotNull(message = "비밀번호 누락됨",groups = {ValidationGroup.Login.class})
    private String password;

    @Email(message = "이메일 형식이 맞지않음")
    private String email;
}
