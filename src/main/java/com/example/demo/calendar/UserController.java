package com.example.demo.calendar;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/login")
    public User login(@RequestBody User user) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        return userDao.login(user);
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws SQLException, ClassNotFoundException {
    UserDao userDao = new UserDao();
    userDao.add(user);
    return user;
    }
}
