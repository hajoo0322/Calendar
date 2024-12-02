package com.example.demo.calendar.Controller;

import com.example.demo.calendar.entity.User;
import com.example.demo.calendar.service.UserDao;
import com.example.demo.calendar.entity.UserRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public User login(@RequestBody User user) throws SQLException, ClassNotFoundException {
        return userDao.login(user);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) throws SQLException, ClassNotFoundException {
    userDao.add(user);
    return user;
    }

    @PutMapping("/changeUserName")
    public User changeUserName(@RequestBody UserRequest user) throws SQLException, ClassNotFoundException {
        return userDao.userNameChanger(user);
    }
}
